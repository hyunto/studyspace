package xyz.hyunto.core.interceptor

import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.mapping.SqlCommandType
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.apache.ibatis.session.ResultHandler
import org.apache.ibatis.session.RowBounds
import org.springframework.stereotype.Component
import xyz.hyunto.core.interceptor.annotations.DualWriteCheck
import xyz.hyunto.core.interceptor.annotations.QueryParam
import java.lang.RuntimeException
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class])
)
@Component
class DualWriteCheckInterceptor : Interceptor {

//	@Autowired
//	lateinit var kafkaTemplate: KafkaTemplate<String, DualWriteCheckMessage>

	companion object {
		val ACCEPTED_SQL_COMMAND_TYPES = listOf(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE)
	}

	override fun intercept(invocation: Invocation?): Any {
		println("### DualWriteConsistencyCheckInterceptor ###")
		if (invocation == null) throw RuntimeException("Invocation cannot be null")

		val result = invocation.proceed()
		println(result)

		if (!ACCEPTED_SQL_COMMAND_TYPES.contains(getMappedStatement(invocation).sqlCommandType) || result == 0) {
			// INSERT, UPDATE, DELETE 요청이 아닌 경우 or
			return result
		}

		val annotation = getAnnotation(invocation) ?: return result as DualWriteCheck
		val parameterMap = getParameterMap(invocation)

		val params = mutableListOf<Map<String, String>>()
		annotation.params.forEach { param ->
			val paramValue = parameterMap[param.name] ?: return@forEach

			if (param.subQueryParams.isEmpty()) {
				// 파라미터가 Primitive 타입
				val key = if (param.mappingName.isNotBlank()) {
					param.mappingName
				} else {
					param.name
				}
				params.add(mapOf(key to paramValue.toString()))
			} else {
				if (paramValue is ArrayList<*>) {
					// 파라미터가 Collection 타입
					paramValue.forEach {
						params.add(getSubParam(it, param))
					}
				} else {
					// 파라미터가 Object 타입
					params.add(getSubParam(paramValue, param))
				}
			}
		}

		println("### Result ###")
		val message = DualWriteCheckMessage(
			tableName = annotation.tableName,
			action = annotation.action,
			query = annotation.query,
			params = params
		)
		println(message)

		if (params.isNotEmpty()) {
			println("# 카프카 메시지 전송")
//			kafkaTemplate.send("dual_write_check", "dual_write_check", message)
		}

		throw RuntimeException("테스트 중...")
//		return result
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
