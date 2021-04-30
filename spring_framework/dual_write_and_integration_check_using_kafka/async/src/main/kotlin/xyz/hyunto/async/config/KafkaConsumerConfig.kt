package xyz.hyunto.async.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonDeserializer
import xyz.hyunto.async.listener.ConsistencyCheckQueueMessage

@Configuration
@EnableKafka
class KafkaConsumerConfig {

	@Value(value = "\${kafka.server.address}")
	lateinit var address: String

	@Bean
	fun consumerFactory(): ConsumerFactory<String, String> {
		return DefaultKafkaConsumerFactory(mapOf(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
		))
	}

	@Bean
	fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
		val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
		factory.consumerFactory = consumerFactory()
		factory.setConcurrency(3)
		factory.containerProperties.pollTimeout = 3000
		factory.setRecordFilterStrategy { record ->
			record.headers().headers("x-custom-header").none {
				val value = String(it.value())
				println("### Custom Header : $value")
				value == "true"
			}
		}
		return factory
	}

	@Bean
	fun consistencyCheckConsumerFactory(): ConsumerFactory<String, ConsistencyCheckQueueMessage> {
		val deserializer: JsonDeserializer<ConsistencyCheckQueueMessage> = JsonDeserializer(ConsistencyCheckQueueMessage::class.java, false)
		deserializer.addTrustedPackages("*")

		return DefaultKafkaConsumerFactory(mapOf(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
		),
			StringDeserializer(),
			deserializer)
	}

	@Bean
	fun consistencyCheckKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ConsistencyCheckQueueMessage>> {
		val factory = ConcurrentKafkaListenerContainerFactory<String, ConsistencyCheckQueueMessage>()
		factory.consumerFactory = consistencyCheckConsumerFactory()
		factory.setConcurrency(3)
		factory.containerProperties.pollTimeout = 3000
		return factory
	}

}
