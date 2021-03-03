package xyz.hyunto.core.interceptor.annotations

import xyz.hyunto.core.interceptor.enums.TableName

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsistencyBulkCheck(
	val tableName: TableName,
	val queryName: String,
	val queryParam: QueryParam
)
