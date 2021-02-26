package xyz.hyunto.async.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.commons.collections4.CollectionUtils
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service
import xyz.hyunto.async.config.DatabaseType
import xyz.hyunto.async.listener.enums.Action
import xyz.hyunto.async.listener.enums.TableName
import xyz.hyunto.async.service.AbstractDualWriteCheck

@Service
class DualWriteCheckListener : MessageListener<String, String> {

	@Autowired
	private lateinit var beanFactory: BeanFactory

	@KafkaListener(topics = ["dual_write_check"], groupId = "dual_write_check", containerFactory = "kafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, String>) {
		println("### DualWriteConsistencyCheckListener")
		val objectMapper = ObjectMapper()
		objectMapper.registerModule(KotlinModule())
		objectMapper.deserializationConfig
		val message = objectMapper.readValue(data.value(), DualWriteCheckMessage::class.java)

		println(message)

		val checkerBO = getChecker(message.tableName)
		message.params.forEach { param ->
			val result1 = checkerBO.execute(DatabaseType.MySQL1, message.query, param)
			val result2 = checkerBO.execute(DatabaseType.MySQL2, message.query, param)

			when (message.action) {
				Action.INSERT, Action.UPDATE -> upsertCheck(result1, result2)
				Action.DELETE -> deleteCheck(result1, result2)
			}
		}
	}

	private fun getChecker(tableName: TableName): AbstractDualWriteCheck {
		return beanFactory.getBean(tableName.checkerName, AbstractDualWriteCheck::class.java)
	}

	private fun upsertCheck(result1: Any?, result2: Any?) {
		println("### Check insert or update")
		println(result1)
		println(result2)
		var result = false
		if (result1 is Collection<*> && result2 is Collection<*>) {
			result = CollectionUtils.isEqualCollection(result1, result2)
		} else {
			result = result1.hashCode() == result2.hashCode()
		}

		println("isConsistency? : $result")
	}

	private fun deleteCheck(result1: Any?, result2: Any?) {
		println("### Check delete")
		println(result1)
		println(result2)
		var result = false
		if (result1 is Collection<*> && result2 is Collection<*>) {
			result = result1.isNullOrEmpty() && result2.isNullOrEmpty()
		} else {
			result = result1 == null && result2 == null
		}
		println("isConsistency? : $result")
	}

}
