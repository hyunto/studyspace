package xyz.hyunto.async.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service
import xyz.hyunto.async.mapper.UserMapper
import xyz.hyunto.core.interceptor.DatabaseTypeHolder
import xyz.hyunto.core.interceptor.DualWriteCheckMessage

@Service
class DualWriteCheckListener : MessageListener<String, DualWriteCheckMessage> {

	@Autowired
	private lateinit var userMapper: UserMapper

	@KafkaListener(topics = ["dual_write_check"], groupId = "dual_write_check", containerFactory = "consistencyCheckKafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, DualWriteCheckMessage>?) {
		val message = data?.value() ?: throw RuntimeException("ConsistencyCheckQueueMessage is null")

		println("### DualWriteConsistencyCheckListener")
		println(message)

		DatabaseTypeHolder.setMySql1()
		println("MySQL1 : ${userMapper.selectById(1)}")
		DatabaseTypeHolder.setMySql2()
		println("MySQL2 : ${userMapper.selectById(1)}")


	}

//	private fun getMapper(targetTable: String): List<Any> {
//		return when (TableName.valueOf(targetTable)) {
//			TableName.USER -> listOf(userMySql1Mapper, userMySql2Mapper)
//			TableName.GROUP -> listOf(groupMySql1Mapper, groupMySql2Mapper)
//			else -> throw RuntimeException()
//		}
//	}
}
