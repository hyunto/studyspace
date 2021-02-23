package xyz.hyunto.core.interceptor

import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName

@Deprecated(message = "쿼리명 + 파라미터 목록 기반으로 다시 개발 예정")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsistencyCheckByProperties(
	val tableName: TableName,
	val action: Action,
	val properties: Array<String>
)
