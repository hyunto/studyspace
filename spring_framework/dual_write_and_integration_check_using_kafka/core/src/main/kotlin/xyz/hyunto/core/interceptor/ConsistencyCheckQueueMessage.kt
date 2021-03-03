package xyz.hyunto.core.interceptor

import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName
import java.time.ZonedDateTime

data class ConsistencyCheckQueueMessage(
	val tableName: TableName,
	val action: Action,
	val queryName: String,
	val queryParams: List<List<Any?>>,
	val activeDatabase: DatabaseType,
	val executionDateTime: ZonedDateTime
)
