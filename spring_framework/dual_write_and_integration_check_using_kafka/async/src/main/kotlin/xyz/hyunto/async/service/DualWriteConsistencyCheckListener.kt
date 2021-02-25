package xyz.hyunto.async.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service
import xyz.hyunto.async.mapper.GroupMySql1Mapper
import xyz.hyunto.async.mapper.GroupMySql2Mapper
import xyz.hyunto.async.mapper.UserMySql1Mapper
import xyz.hyunto.async.mapper.UserMySql2Mapper
import xyz.hyunto.core.interceptor.DualWriteCheckMessage

@Service
class DualWriteConsistencyCheckListener : MessageListener<String, DualWriteCheckMessage> {

	@Autowired
	lateinit var userMySql1Mapper: UserMySql1Mapper

	@Autowired
	lateinit var userMySql2Mapper: UserMySql2Mapper

	@Autowired
	lateinit var groupMySql1Mapper: GroupMySql1Mapper

	@Autowired
	lateinit var groupMySql2Mapper: GroupMySql2Mapper

	@KafkaListener(topics = ["dual_write_check"], groupId = "consistency_check-group", containerFactory = "consistencyCheckKafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, DualWriteCheckMessage>?) {
		val message = data?.value() ?: throw RuntimeException("ConsistencyCheckQueueMessage is null")

		println("### DualWriteConsistencyCheckListener")
		println(message)


	}

//	private fun getMapper(targetTable: String): List<Any> {
//		return when (TableName.valueOf(targetTable)) {
//			TableName.USER -> listOf(userMySql1Mapper, userMySql2Mapper)
//			TableName.GROUP -> listOf(groupMySql1Mapper, groupMySql2Mapper)
//			else -> throw RuntimeException()
//		}
//	}
}
