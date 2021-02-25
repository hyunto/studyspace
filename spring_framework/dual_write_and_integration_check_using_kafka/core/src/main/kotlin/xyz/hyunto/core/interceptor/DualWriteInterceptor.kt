package xyz.hyunto.core.interceptor

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
import org.springframework.stereotype.Component
import xyz.hyunto.core.interceptor.annotations.DualWriteCheck
import xyz.hyunto.core.interceptor.annotations.QueryParam
import java.lang.RuntimeException
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class, CacheKey::class, BoundSql::class])
)
class DualWriteInterceptor @Autowired constructor(
	private val dualWriteKafkaTemplate: KafkaTemplate<String, DualWriteCheckMessage>
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
			val databaseType = DatabaseTypeHolder.get()
			if (databaseType == DatabaseType.MySQL1 || result == 0)  return result

			val annotation = getAnnotation(invocation) ?: return result as DualWriteCheck
			getQueryParamsForCheck(invocation, annotation).takeIf { it.isNotEmpty() }?.run {
				val message = DualWriteCheckMessage(
					tableName = annotation.tableName,
					action = annotation.action,
					query = annotation.query,
					params = this
				)
				println("# 카프카 메시지 전송 : $message")
				dualWriteKafkaTemplate.send("dual_write_check", "dual_write_check", message)
			}

			return result
		} else {
			setDataSource()
			val result = invocation.proceed()
			DatabaseTypeHolder.clear()
			return result
		}
	}

	private fun getQueryParamsForCheck(invocation: Invocation, annotation: DualWriteCheck): List<Map<String, String>> {
		val parameterMap = getParameterMap(invocation)
		val results = mutableListOf<Map<String, String>>()
		annotation.params.forEach { param ->
			val paramValue = parameterMap[param.name] ?: return@forEach

			if (param.subQueryParams.isEmpty()) {
				// 파라미터가 Primitive 타입
				val key = if (param.mappingName.isNotBlank()) {
					param.mappingName
				} else {
					param.name
				}
				results.add(mapOf(key to paramValue.toString()))
			} else {
				if (paramValue is ArrayList<*>) {
					// 파라미터가 Collection 타입
					paramValue.forEach {
						results.add(getSubParam(it, param))
					}
				} else {
					// 파라미터가 Object 타입
					results.add(getSubParam(paramValue, param))
				}
			}
		}
		return results
	}

	private fun getSubParam(instance: Any, param: QueryParam): Map<String, String> {
		val result = mutableMapOf<String, String>()
		param.subQueryParams.forEach { subParam ->
			val key = if (subParam.mappingName.isNotBlank()) {
				subParam.mappingName
			} else {
				subParam.name
			}
			result[key] = getValueOfField(instance, subParam.name)
		}
		return result
	}

	private fun getValueOfField(instance: Any, propertyName: String): String {
		val property = instance::class.members.first { it.name == propertyName } as KProperty1<Any, *>
		return property.get(instance).toString()
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
