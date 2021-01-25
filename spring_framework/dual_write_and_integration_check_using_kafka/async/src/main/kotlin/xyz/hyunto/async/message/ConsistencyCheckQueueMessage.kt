package xyz.hyunto.async.message

import xyz.hyunto.async.model.enums.Action
import java.time.ZonedDateTime

data class ConsistencyCheckQueueMessage(
	val targetTable: String,
	val targetId: Long,
	val action: Action,
	val createDateTime: ZonedDateTime
)
