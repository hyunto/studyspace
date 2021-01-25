package xyz.hyunto.async.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service

@Service
class TestMessageListener(): MessageListener<String, String> {

	@KafkaListener(topics = ["test"], groupId = "test-group", containerFactory = "kafkaListenerContainerFactory")
	override fun onMessage(data: ConsumerRecord<String, String>?) {
		println("#####")
		println(data)
		println("#####")
	}

}
