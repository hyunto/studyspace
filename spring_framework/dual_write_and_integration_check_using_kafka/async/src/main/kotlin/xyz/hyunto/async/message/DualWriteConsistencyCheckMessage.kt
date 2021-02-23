package xyz.hyunto.async.message

import xyz.hyunto.async.model.enums.Action
import xyz.hyunto.async.model.enums.TableName
import java.time.ZonedDateTime

data class DualWriteConsistencyCheckMessage(
	val tableName: TableName,
	val action: Action,
	val query: String,
	val params: List<Map<String, String>>
)
