package xyz.hyunto.async.service

import xyz.hyunto.async.mapper.DualWriteInconsistencyMapper
import xyz.hyunto.async.message.ConsistencyCheckQueueMessage
import xyz.hyunto.async.model.enums.Action
import xyz.hyunto.async.model.enums.TableName

abstract class AbstractConsistencyCheckService<out T>(
	private var dualWriteInconsistencyMapper: DualWriteInconsistencyMapper
) {

	fun diff(message: ConsistencyCheckQueueMessage) {
		val data1 = getFromMySql1(message.targetId)
		val data2 = getFromMySql2(message.targetId)

		val isEqual = when (message.action) {
			Action.INSERT -> (data1 != null && data2 != null) && (data1 == data2)
			Action.UPDATE -> (data1 != null && data2 != null) && (data1 == data2)
			Action.DELETE -> data1 == null && data2 == null
		}

		if (!isEqual) writeInconsistencyLog(message)
	}

	private fun writeInconsistencyLog(message: ConsistencyCheckQueueMessage) {
		with(message) {
			dualWriteInconsistencyMapper.insert(TableName.valueOf(tableName.value), targetId, action)
		}
	}

	abstract fun getFromMySql1(id: Long): T?

	abstract fun getFromMySql2(id: Long): T?

}
