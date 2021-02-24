package xyz.hyunto.core.config.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import xyz.hyunto.core.interceptor.DualWriteConsistencyCheckMessage

@Configuration
@EnableKafka
class KafkaProducerConfig {

	@Value(value = "\${kafka.server.address}")
	lateinit var address: String

	@Bean
	fun producerFactory(): ProducerFactory<String, String> {
		return DefaultKafkaProducerFactory(mapOf(
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
		))
	}

	@Bean
	fun consistencyCheckProducerFactory(): ProducerFactory<String, DualWriteConsistencyCheckMessage> {
		return DefaultKafkaProducerFactory(mapOf(
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
		))
	}

	@Bean
	fun kafkaTemplate(): KafkaTemplate<String, String> {
		return KafkaTemplate(producerFactory())
	}

}
