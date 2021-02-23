package xyz.hyunto.core.interceptor

import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName

data class Message(
	val tableName: TableName,
	val action: Action,
	val query: String,
	val params: List<Map<String, String>>
)
