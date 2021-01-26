package xyz.hyunto.core.aspect

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DualWriteConsistencyCheck<T: Any>(
	val id: String,
	val model: KClass<T>
)
