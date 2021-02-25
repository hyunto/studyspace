package xyz.hyunto.core.interceptor.annotations

import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DualWriteCheck(
	val tableName: TableName,	// 인터셉터로 Mapper 이름을 가져올 수 있지 않을까?
	val action: Action,
	val query: String,
	val params: Array<QueryParam> = []
)
