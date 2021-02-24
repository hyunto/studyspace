package xyz.hyunto.async.config.kafka

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
import xyz.hyunto.core.interceptor.DualWriteConsistencyCheckMessage

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
		return factory
	}

	@Bean
	fun consistencyCheckConsumerFactory(): ConsumerFactory<String, DualWriteConsistencyCheckMessage> {
		return DefaultKafkaConsumerFactory(mapOf(
			ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
			ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
			ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java
		))
	}

	@Bean
	fun consistencyCheckKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DualWriteConsistencyCheckMessage>> {
		val factory = ConcurrentKafkaListenerContainerFactory<String, DualWriteConsistencyCheckMessage>()
		factory.consumerFactory = consistencyCheckConsumerFactory()
		factory.setConcurrency(3)
		factory.containerProperties.pollTimeout = 3000
		return factory
	}

}
