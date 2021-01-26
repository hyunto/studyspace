package xyz.hyunto.core.interceptor

import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.springframework.util.ClassUtils
import xyz.hyunto.core.model.User
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaGetter

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class])
)
class ConsistencyCheckInterceptor(): Interceptor {

	companion object {
		const val ANNOTATION_NAME = "ConsistencyCheckInfo"
	}

	override fun intercept(invocation: Invocation?): Any {

		if (invocation == null) {
			throw RuntimeException("Invocation is null")
		}

		println("##### ConsistencyCheckInterceptor #####")

		when(val annotation = getAnnotation(invocation)) {
			is ConsistencyCheckById -> processById(invocation, annotation)
			is ConsistencyCheckByProperties -> processByProperties(invocation, annotation)
		}

		println("######")

		return invocation.proceed()
	}

	private fun getAnnotation(invocation: Invocation): Any {
		val mappedStatement = invocation.args[0] as MappedStatement
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz =  Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method : ${methodName}")
		return method.annotations.firstOrNull { it.annotationClass == ConsistencyCheckById::class } as? ConsistencyCheckById
			?: method.annotations.firstOrNull{ it.annotationClass == ConsistencyCheckByProperties::class} as? ConsistencyCheckByProperties
			?: throw RuntimeException("cannot find annotation : @ConsistencyCheckInfo")
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

	private fun processById(invocation: Invocation, annotation: ConsistencyCheckById) {
		println("*** ConsistencyCheckById ***")
		println(annotation.id)
		println(annotation.action)
		println(annotation.tableName.value)
		println(annotation.type)

		val parameterMap = getParameterMap(invocation)
		val id: Any? = if (ClassUtils.isPrimitiveOrWrapper(annotation.type::class.java)) {
			parameterMap[annotation.id]
		} else {
			val model = parameterMap.values.firstOrNull { it::class == annotation.type }?.javaClass?.kotlin
			if (model != null) {
				val result = model::class.memberProperties.firstOrNull { it.name == "name" }
				result?.get(this)
			} else {
				throw RuntimeException("id is null")
			}
		}
		println(id)
	}

	private fun processByProperties(invocation: Invocation, annotation: ConsistencyCheckByProperties) {
		println("*** ConsistencyCheckByProperties ***")
		println(annotation.tableName.value)
		println(annotation.action)
		println(annotation.properties)
	}

}
