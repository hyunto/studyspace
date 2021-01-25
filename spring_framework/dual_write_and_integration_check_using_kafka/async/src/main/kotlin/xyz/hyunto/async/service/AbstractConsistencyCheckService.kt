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

		val isInconsistency = when (message.action) {
			Action.INSERT -> deepDiff(data1, data2)
			Action.UPDATE -> deepDiff(data1, data2)
			Action.DELETE -> data1 == null && data2 == null
		}

		if (isInconsistency) writeInconsistencyLog(message)
	}

	private fun deepDiff(data1: T?, data2: T?): Boolean {
		if (data1 == null || data2 == null) {
			return false
		}
		TODO("리플렉션으로 데이터 클래스 값 비교")
		return true
	}

	private fun writeInconsistencyLog(message: ConsistencyCheckQueueMessage) {
		with(message) {
			dualWriteInconsistencyMapper.insert(TableName.valueOf(targetTable), targetId, action)
		}
	}

	abstract fun getFromMySql1(id: Long): T?

	abstract fun getFromMySql2(id: Long): T?

}
