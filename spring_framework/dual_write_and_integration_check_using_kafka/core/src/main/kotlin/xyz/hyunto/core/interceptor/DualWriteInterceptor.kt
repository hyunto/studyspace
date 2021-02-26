package xyz.hyunto.core.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.cache.CacheKey
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.BoundSql
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.RowBounds
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import xyz.hyunto.core.interceptor.annotations.DualWriteCheck
import xyz.hyunto.core.interceptor.annotations.QueryParam
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class, CacheKey::class, BoundSql::class])
)
class DualWriteInterceptor @Autowired constructor(
	private val kafkaTemplate: KafkaTemplate<String, String>
) : Interceptor {

	companion object {
		val ACCEPTED_SQL_COMMAND_TYPES = listOf(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE)
		val ACTIVE_DATABASE = DatabaseType.MySQL1
	}

	private fun setDataSource() {
		DatabaseTypeHolder.set(ACTIVE_DATABASE)
	}

	override fun intercept(invocation: Invocation): Any {
		println("### DualWriteInterceptor ###")

		if (ACCEPTED_SQL_COMMAND_TYPES.contains(getMappedStatement(invocation).sqlCommandType)) {
			val result = invocation.proceed()
			if (DatabaseTypeHolder.get() == DatabaseType.MySQL1
			//				|| result == 0
			) return result

			val annotation = getAnnotation(invocation) ?: return result as DualWriteCheck
			getQueryParams(invocation, annotation).takeIf { it.isNotEmpty() }?.run {
				val message = DualWriteCheckMessage(
					tableName = annotation.tableName,
					action = annotation.action,
					query = annotation.query,
					params = this
				)
				val objectMapper = ObjectMapper()
				println("# 카프카 메시지 전송 : ${objectMapper.writeValueAsString(message)}")
				kafkaTemplate.send("dual_write_check", "dual_write_check", objectMapper.writeValueAsString(message))
			}

			return result
		} else {
			setDataSource()
			val result = invocation.proceed()
			DatabaseTypeHolder.clear()
			return result
		}
	}

	private fun getQueryParams(invocation: Invocation, annotation: DualWriteCheck): List<List<Any>> {
		val parameterMap = getParameterMap(invocation)
		val results = mutableListOf<List<Any>>()

		if (annotation.multiple) {
			annotation.params.firstOrNull()?.run {
				val values = parameterMap[this.name] ?: return@run
				if (values is ArrayList<*>) {
					values.forEach {
						results.add(getSubParamValue(it, this))
					}
				}
			}
		} else {
			val result = mutableListOf<Any>()
			annotation.params.forEach { param ->
				val value = parameterMap[param.name] ?: return@forEach
				if (param.subQueryParams.isEmpty()) {
					result.add(value)
				} else {
					if (value is ArrayList<*>) {
						val temp = mutableListOf<Any>()
						value.forEach {
							temp.add(getSubParamValue(it, param))
						}
						result.add(temp)
					} else {
						results.add(getSubParamValue(value, param))
						return@forEach
					}
				}
			}
			results.add(result)
		}
		return results.filterNot { it.isEmpty() }
	}

	private fun getSubParamValue(instance: Any, param: QueryParam): List<Any> {
		val results = mutableListOf<Any>()
		param.subQueryParams.forEach { subParam ->
			results.add(getParamValue(instance, subParam.name))
		}
		return results
	}

	private fun getParamValue(instance: Any, name: String): Any {
		val property = instance::class.members.first { it.name == name } as KProperty1<Any, *>
		return property.get(instance) ?: throw RuntimeException("value is null")
	}

	private fun getAnnotation(invocation: Invocation): DualWriteCheck? {
		val mappedStatement = getMappedStatement(invocation)
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz = Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method (expected: $methodName)")
		return method.annotations.firstOrNull { it.annotationClass == DualWriteCheck::class } as? DualWriteCheck
	}

	private fun getMappedStatement(invocation: Invocation): MappedStatement {
		return invocation.args[0] as MappedStatement
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

}
