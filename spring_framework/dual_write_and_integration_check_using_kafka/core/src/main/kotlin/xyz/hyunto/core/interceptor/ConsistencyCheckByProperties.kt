package xyz.hyunto.core.interceptor

import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsistencyCheckByProperties(
	val tableName: TableName,
	val action: Action,
	val properties: Array<String>
)
