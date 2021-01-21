package xyz.hyunto.core.config.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig {

	@Value(value = "kafka.server.address")
	lateinit var address: String

	@Bean
	fun kafkaAdmin(): KafkaAdmin {
		return KafkaAdmin(mapOf(
			AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to address
		))
	}

	@Bean
	fun integrityCheckTopic(): NewTopic {
		return TopicBuilder.name("integrity_check")
			.partitions(10)
			.replicas(3)
			.compact()
			.build();
	}

}
