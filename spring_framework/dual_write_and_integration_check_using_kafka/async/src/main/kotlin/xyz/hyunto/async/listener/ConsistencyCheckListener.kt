package xyz.hyunto.async.listener

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.commons.collections4.CollectionUtils
import org.apache.ibatis.session.SqlSessionFactory
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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import kotlin.reflect.KCallable

@Service
class ConsistencyCheckListener : MessageListener<String, String> {

	@Autowired
	private lateinit var beanFactory: BeanFactory

	@Autowired
	private lateinit var sqlSessionFactory: SqlSessionFactory

	private fun getObjectMapper(): ObjectMapper {
		val javaTimeModule = JavaTimeModule()
		javaTimeModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
		javaTimeModule.addDeserializer(ZonedDateTime::class.java, object : JsonDeserializer<ZonedDateTime>() {
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
		objectMapper.deserializationConfig
		val message = objectMapper.readValue(data.value(), ConsistencyCheckQueueMessage::class.java)

		println(message)

		/** 방법 1 */
//		val mapper = getMapper(message.tableName)
//		val method = mapper::class.members.firstOrNull { it.name == message.queryName } ?: throw RuntimeException("not found method (mapper: ${message.tableName.mapper.simpleName}, method: ${message.queryName}")
//		message.queryParams.forEach { param ->
//			DatabaseTypeHolder.set(DatabaseType.MySQL1)
//			val result1 = method.call(mapper, *param.toTypedArray())
//			DatabaseTypeHolder.set(DatabaseType.MySQL2)
//			val result2 = method.call(mapper, *param.toTypedArray())
//
//			when (message.action) {
//				Action.INSERT, Action.UPDATE -> upsertCheck(result1, result2)
//				Action.DELETE -> deleteCheck(result1, result2)
//			}
//		}

		/** 방법 2 */
//		message.queryParams.forEach { param ->
//			DatabaseTypeHolder.set(DatabaseType.MySQL1)
//			val sqlSession1 = sqlSessionFactory.openSession()
//			val mapper1 = sqlSession1.getMapper(message.tableName.mapper)
//			val method1 = mapper1::class.members.firstOrNull { it.name == message.queryName } ?: throw RuntimeException("not found method (mapper: ${message.tableName.mapper.simpleName}, method: ${message.queryName}")
//			val result1 = method1.call(mapper1, *param.toTypedArray())
//			sqlSession1.commit()
//			println("result1 : $result1")
//
//			DatabaseTypeHolder.set(DatabaseType.MySQL2)
//			val sqlSession2 = sqlSessionFactory.openSession()
//			val mapper2 = sqlSession2.getMapper(message.tableName.mapper)
//			val method2 = mapper2::class.members.firstOrNull { it.name == message.queryName } ?: throw RuntimeException("not found method (mapper: ${message.tableName.mapper.simpleName}, method: ${message.queryName}")
//			val result2 = method2.call(mapper2, *param.toTypedArray())
//			sqlSession2.commit()
//			println("result2 : $result2")
//
//			println("isEqual? : ${isEqual(result1, result2)}")
//		}

		/** 방법 3 */
		message.queryParams.forEach { param ->
			DatabaseTypeHolder.set(DatabaseType.MySQL1)
			val result1 = execute(message.tableName.mapper, message.queryName, param)

			DatabaseTypeHolder.set(DatabaseType.MySQL2)
			val result2 = execute(message.tableName.mapper, message.queryName, param)

			println("# result1 : $result1")
			println("# result2 : $result2")
			println("isEqual? : ${isEqual(result1, result2)}")
		}
	}

	private fun execute(mapperClass: Class<*>, queryName: String, queryParam: List<Any?>): Any? {
		val sqlSession = sqlSessionFactory.openSession()
		val mapper = sqlSession.getMapper(mapperClass)
		val method = mapper::class.members.first { it.name == queryName }
		val result = method.call(mapper, *queryParam.toTypedArray())
		sqlSession.commit()
		return result
	}

	private fun isEqual(primaryResult: Any?, secondaryResult: Any?): Boolean {
		if ((primaryResult == null && secondaryResult != null) || (primaryResult != null && secondaryResult == null))
			return false

		if (primaryResult is Collection<*> && secondaryResult is Collection<*>)
			return CollectionUtils.isEqualCollection(primaryResult, secondaryResult)

		if (primaryResult != secondaryResult)
			return false

		return true
	}

	private fun getMapper(tableName: TableName): Any {
		return beanFactory.getBean(tableName.mapper)
	}

}
