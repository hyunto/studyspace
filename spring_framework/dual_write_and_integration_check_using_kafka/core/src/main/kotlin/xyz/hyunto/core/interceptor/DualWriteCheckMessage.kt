package xyz.hyunto.core.interceptor

import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName

data class DualWriteCheckMessage(
	val tableName: TableName,
	val action: Action,
	val query: String,
	val params: List<List<Any>>
)
