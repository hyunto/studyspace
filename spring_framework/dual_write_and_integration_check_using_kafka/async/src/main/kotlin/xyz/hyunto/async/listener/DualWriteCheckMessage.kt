package xyz.hyunto.async.listener

import xyz.hyunto.async.listener.enums.Action
import xyz.hyunto.async.listener.enums.TableName

data class DualWriteCheckMessage(
	val tableName: TableName,
	val action: Action,
	val query: String,
	val params: List<Map<String, String>>
)
