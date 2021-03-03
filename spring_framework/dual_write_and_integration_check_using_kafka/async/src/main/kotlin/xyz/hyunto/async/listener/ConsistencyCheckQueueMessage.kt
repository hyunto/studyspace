package xyz.hyunto.async.listener

import xyz.hyunto.async.config.DatabaseType
import xyz.hyunto.async.listener.enums.Action
import xyz.hyunto.async.listener.enums.TableName
import java.time.ZonedDateTime

data class ConsistencyCheckQueueMessage(
	val tableName: TableName,
	val action: Action,
	val queryName: String,
	val queryParams: List<List<Any?>>,
	val activeDatabase: DatabaseType,
	val executionDateTime: ZonedDateTime
)
