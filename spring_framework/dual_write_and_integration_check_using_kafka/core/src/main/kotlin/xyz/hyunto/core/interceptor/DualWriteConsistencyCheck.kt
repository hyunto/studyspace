package xyz.hyunto.core.interceptor

import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DualWriteConsistencyCheck(
	val tableName: TableName,	// 인터셉터로 Mapper 이름을 가져올 수 있지 않을까?
	val action: Action,
	val query: String,
	val params: Array<QueryParam>
)
