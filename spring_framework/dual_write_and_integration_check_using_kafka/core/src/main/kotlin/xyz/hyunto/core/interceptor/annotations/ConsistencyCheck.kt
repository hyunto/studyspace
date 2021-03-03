package xyz.hyunto.core.interceptor.annotations

import xyz.hyunto.core.interceptor.enums.TableName

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsistencyCheck(
	val tableName: TableName,
	val queryName: String,
	val queryParams: Array<QueryParam> = []
)
