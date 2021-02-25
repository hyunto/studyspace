package xyz.hyunto.async.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.listener.MessageListener
import xyz.hyunto.async.mapper.UserMySql1Mapper
import xyz.hyunto.async.mapper.UserMySql2Mapper
import xyz.hyunto.core.interceptor.DualWriteCheckMessage

//class UserConsistencyCheckService : MessageListener<String, DualWriteConsistencyCheckMessage>, AbstractConsistencyCheckService<User> {
class UserConsistencyCheckService : MessageListener<String, DualWriteCheckMessage> {

	@Autowired
	private lateinit var userMySql1Mapper: UserMySql1Mapper

	@Autowired
	private lateinit var userMySql2Mapper: UserMySql2Mapper


	override fun onMessage(data: ConsumerRecord<String, DualWriteCheckMessage>?) {
		val message = data?.value() ?: throw RuntimeException("ConsistencyCheckQueueMessage is null")
//		diff(message)
	}

//	override fun getFromMySql1(id: Long): User? {
//		return userMySql1Mapper.selectById(id)
//	}
//
//	override fun getFromMySql2(id: Long): User? {
//		return userMySql2Mapper.selectById(id)
//	}
}
