package xyz.hyunto.async.message

import xyz.hyunto.async.model.enums.Action
import xyz.hyunto.async.model.enums.TableName
import java.time.ZonedDateTime

data class ConsistencyCheckQueueMessage(
	val tableName: TableName,
	val action: Action,
	val targetId: Long?,
	val properties: Map<String, String>?,
	val actionDateTime: ZonedDateTime = ZonedDateTime.now()
)
