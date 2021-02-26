package xyz.hyunto.async.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service
import xyz.hyunto.async.config.DatabaseType
import xyz.hyunto.async.config.DatabaseTypeHolder
import xyz.hyunto.async.listener.enums.Action
import xyz.hyunto.async.listener.enums.TableName
import xyz.hyunto.async.mapper.UserMapper
import xyz.hyunto.async.service.AbstractDualWriteCheck

@Service
class DualWriteCheckListener : MessageListener<String, DualWriteCheckMessage> {

	@Autowired
	private lateinit var userMapper: UserMapper

	@Autowired
	private lateinit var beanFactory: BeanFactory

	@KafkaListener(topics = ["dual_write_check"], groupId = "dual_write_check", containerFactory = "consistencyCheckKafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, DualWriteCheckMessage>?) {
		val message = data?.value() ?: throw RuntimeException("ConsistencyCheckQueueMessage is null")

		println("### DualWriteConsistencyCheckListener")
		println(message)

		val checkerBO = getChecker(message.tableName)
		message.params.forEach { param ->
			val result1 = checkerBO.execute(DatabaseType.MySQL1, message.query, listOf(1))
			val result2 = checkerBO.execute(DatabaseType.MySQL2, message.query, listOf(1))

			when(message.action) {
				Action.INSERT, Action.UPDATE -> upsertCheck(result1, result2)
				Action.DELETE -> deleteCheck(result1, result2)
				else -> throw RuntimeException("Unknown action type: $this")
			}
		}
	}

	private fun getChecker(tableName: TableName): AbstractDualWriteCheck {
		return beanFactory.getBean(tableName.checkerName, AbstractDualWriteCheck::class.java)
	}

	private fun upsertCheck(result1: Any?, result2: Any?) {
		println("Check insert or update")
		println(result1)
		println(result2)
	}

	private fun deleteCheck(result1: Any?, result2: Any?) {
		println("Check delete")
	}

}
