package xyz.hyunto.core.interceptor

import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.executor.Executor
import org.apache.ibatis.mapping.MappedStatement
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import java.lang.RuntimeException
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

@Intercepts(
	Signature(type = Executor::class, method = "update", args = [MappedStatement::class, Object::class])
)
class DualWriteConsistencyCheckInterceptor : Interceptor {

	override fun intercept(invocation: Invocation?): Any {
		println("### DualWriteConsistencyCheckInterceptor ###")
		if (invocation == null) throw RuntimeException("Invocation cannot be null")

		val annotation = getAnnotation(invocation) ?: invocation.proceed() as DualWriteConsistencyCheck
		val parameterMap = getParameterMap(invocation)

		val data = mutableMapOf<String, String>()
		val dataList = mutableListOf<Map<String, String>>()
		annotation.params.forEach { param ->
			val paramValue = parameterMap[param.name] ?: return@forEach
			println(paramValue)

			if (param.subParams.isEmpty()) {
				// 파라미터가 Primitive 타입
				data[param.name] = paramValue.toString()
			} else if (param.subParams.isNotEmpty() && !param.isCollection) {
				// 파라미터가 Object 타입
				param.subParams.forEach { subParam ->
					data[subParam.name] = getValueOfField(paramValue, subParam.name)
				}
			} else if (param.subParams.isNotEmpty() && param.isCollection) {
				// 파라미터가 Collection 타입

				(paramValue as ArrayList<*>).forEach {
					println(it)
					val tempData = mutableMapOf<String, String>()
					param.subParams.forEach { subParam ->
						tempData[subParam.name] = getValueOfField(it, subParam.name)
					}
					dataList.add(tempData)
				}
			}
		}

		println("### Result ###")
		println(data)
		println(dataList)

		return invocation.proceed()
	}

	private fun getValueOfField(instance: Any, propertyName: String): String {
		val property = instance::class.members.first { it.name == propertyName } as KProperty1<Any, *>
		return property.get(instance).toString()
	}

	private fun getAnnotation(invocation: Invocation): DualWriteConsistencyCheck? {
		val mappedStatement = invocation.args[0] as MappedStatement
		val className = mappedStatement.id.substringBeforeLast(".")
		val methodName = mappedStatement.id.substringAfterLast(".")

		val clazz = Class.forName(className).kotlin
		val method = clazz.memberFunctions.firstOrNull { it.name == methodName } ?: throw RuntimeException("cannot find method (expected: $methodName)")
		return method.annotations.firstOrNull { it.annotationClass == DualWriteConsistencyCheck::class } as? DualWriteConsistencyCheck
	}

	private fun getParameterMap(invocation: Invocation): MapperMethod.ParamMap<*> {
		return invocation.args[1] as MapperMethod.ParamMap<*>
	}

}
