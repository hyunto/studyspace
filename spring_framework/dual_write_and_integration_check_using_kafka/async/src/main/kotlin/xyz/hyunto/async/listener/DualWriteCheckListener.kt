package xyz.hyunto.async.listener

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

@Service
class DualWriteCheckListener : MessageListener<String, String> {

	@Autowired
	private lateinit var beanFactory: BeanFactory

	private fun getObjectMapper(): ObjectMapper {
		val javaTimeModule = JavaTimeModule()
		javaTimeModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
		javaTimeModule.addDeserializer(ZonedDateTime::class.java, object: JsonDeserializer<ZonedDateTime>() {
			override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): ZonedDateTime {
				return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatterBuilder()
					.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
					.optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd() // ISO-8601
					.optionalStart().appendOffset("+HH:MM", "Z").optionalEnd() // ISO-8601
					.optionalStart().appendOffset("+HHMM", "+0000").optionalEnd() // legacy
					.toFormatter()
				)
			}

		})

		val objectMapper = ObjectMapper()
		objectMapper.registerModule(KotlinModule())
		objectMapper.registerModule(javaTimeModule)
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //모르는 프라퍼티에 대해서는 무시..
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 표준 사용
		return objectMapper
	}

	@KafkaListener(topics = ["dual_write_check"], groupId = "dual_write_check", containerFactory = "kafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, String>) {
		println("### DualWriteConsistencyCheckListener")
		val objectMapper = getObjectMapper()
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		objectMapper.deserializationConfig
		val message = objectMapper.readValue(data.value(), ConsistencyCheckQueueMessage::class.java)

		println(message)

		val checkerBO = getChecker(message.tableName)
		message.queryParams.forEach { param ->
			val result1 = checkerBO.execute(DatabaseType.MySQL1, message.queryName, param)
			val result2 = checkerBO.execute(DatabaseType.MySQL2, message.queryName, param)

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
