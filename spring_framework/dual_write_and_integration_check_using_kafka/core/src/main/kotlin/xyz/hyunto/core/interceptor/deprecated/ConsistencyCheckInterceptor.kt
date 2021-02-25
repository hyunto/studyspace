package xyz.hyunto.core.interceptor.deprecated

import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.springframework.util.ClassUtils
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions

@Deprecated(message = "쿼리명 + 파라미터 목록 기반으로 다시 개발 예정")
@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class])
)
class ConsistencyCheckInterceptor() : Interceptor {

	override fun intercept(invocation: Invocation?): Any {
		println("### ConsistencyCheckInterceptor ###")

		if (invocation == null) {
			throw RuntimeException("Invocation is null")
		}

		when (val annotation = getAnnotation(invocation)) {
			is ConsistencyCheckById -> processById(invocation, annotation)
			is ConsistencyCheckByProperties -> processByProperties(invocation, annotation)
			else -> invocation.proceed()
		}

		return invocation.proceed()
	}

	private fun getAnnotation(invocation: Invocation): Any? {
		val mappedStatement = invocation.args[0] as MappedStatement
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz = Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method (expected: $methodName)")
		return method.annotations.firstOrNull { it.annotationClass == ConsistencyCheckById::class } as? ConsistencyCheckById
			?: method.annotations.firstOrNull { it.annotationClass == ConsistencyCheckByProperties::class } as? ConsistencyCheckByProperties
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

	private fun getId(instance: Any, propertyName: String): Any {
		val property = instance::class.members.firstOrNull { it.name == propertyName } as? KProperty1<Any, *>
		return property?.get(instance) ?: throw RuntimeException("No element matching the predicate. (expected: $propertyName)")
	}

	private fun processById(invocation: Invocation, annotation: ConsistencyCheckById) {
		val parameterMap = getParameterMap(invocation)
		val id: Any = if (ClassUtils.isPrimitiveOrWrapper(annotation.type.java)) {
			parameterMap[annotation.id]
		} else {
			parameterMap.values.firstOrNull { it::class == annotation.type }?.let { getId(it, annotation.id) }
		} ?: throw RuntimeException("No element matching the predicate. (expected: ${annotation.type})")

		val message = mapOf(
			"table" to annotation.tableName,
			"id" to id,
			"action" to annotation.action
		)
		println("message : $message")

		// TODO: 카프카로 메시지 전송
	}

	private fun processByProperties(invocation: Invocation, annotation: ConsistencyCheckByProperties) {
		val parameterMap = getParameterMap(invocation)
		val data = mutableMapOf<String, String>()
		annotation.properties.toList()
			.forEach { parameterMap[it]?.run { data[it] = this.toString() } ?: throw RuntimeException("Parameter named '$it' is null") }

		val message = mapOf(
			"table" to annotation.tableName,
			"action" to annotation.action,
			"data" to data
		)
		println("message : $message")

		// TODO: 카프카로 메시지 전송
	}

}
