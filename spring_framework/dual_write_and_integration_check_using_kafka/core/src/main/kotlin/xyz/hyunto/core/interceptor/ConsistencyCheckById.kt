package xyz.hyunto.core.interceptor

import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsistencyCheckById(
	val tableName: TableName,
	val action: Action,
	val id: String,
	val type: KClass<out Any>
)
