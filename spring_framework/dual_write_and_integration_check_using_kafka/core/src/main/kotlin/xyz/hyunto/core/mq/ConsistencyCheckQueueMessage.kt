package xyz.hyunto.core.mq

import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName
import java.time.ZonedDateTime

data class ConsistencyCheckQueueMessage(
	val tableName: TableName,
	val action: Action,
	val targetId: Long?,
	val properties: Map<String, String>?,
	val actionDateTime: ZonedDateTime = ZonedDateTime.now()
)
