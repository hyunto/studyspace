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
import java.lang.RuntimeException
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class]),
	Signature(type = Executor::class, method = "query", args = [MappedStatement::class, Object::class, RowBounds::class, ResultHandler::class])
)
class DualWriteConsistencyCheckInterceptor : Interceptor {

	companion object {
		val ACCEPTED_SQL_COMMAND_TYPES = listOf<SqlCommandType>(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE)
	}

	override fun intercept(invocation: Invocation?): Any {
		println("### DualWriteConsistencyCheckInterceptor ###")
		if (invocation == null) throw RuntimeException("Invocation cannot be null")

		if (!ACCEPTED_SQL_COMMAND_TYPES.contains(getMappedStatement(invocation).sqlCommandType)) {
			invocation.proceed()
		}

		val annotation = getAnnotation(invocation) ?: invocation.proceed() as DualWriteConsistencyCheck
		val parameterMap = getParameterMap(invocation)

		val params = mutableListOf<Map<String, String>>()
		annotation.params.forEach { param ->
			val paramValue = parameterMap[param.name] ?: return@forEach

			// TODO: if문 조건절 순서 조정 및 예외 처리 필요
			if (param.subParams.isEmpty()) {
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
		val message = DualWriteConsistencyCheckMessage(
			tableName = annotation.tableName,
			action = annotation.action,
			query = annotation.query,
			params = params
		)
		println(message)

		if (params.isNotEmpty()) {
			// TODO: 카프카 메시지 전송
			println("# 카프카 메시지 전송")
		}

		throw RuntimeException("테스트 중...")
//		return invocation.proceed()
	}

	private fun getSubParam(instance: Any, param: QueryParam): Map<String, String> {
		val result = mutableMapOf<String, String>()
		param.subParams.forEach { subParam ->
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

	private fun getAnnotation(invocation: Invocation): DualWriteConsistencyCheck? {
		val mappedStatement = getMappedStatement(invocation)
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz = Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method (expected: $methodName)")
		return method.annotations.firstOrNull { it.annotationClass == DualWriteConsistencyCheck::class } as? DualWriteConsistencyCheck
	}

	private fun getMappedStatement(invocation: Invocation): MappedStatement {
		return invocation.args[0] as MappedStatement
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

}
