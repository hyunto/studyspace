package xyz.hyunto.core.config.kafka

import org.apache.kafka.clients.producer.ProducerInterceptor
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.header.internals.RecordHeader
import java.lang.Exception

class KafkaProducerInterceptor : ProducerInterceptor<String, String> {
	override fun configure(config: MutableMap<String, *>?) {
		// do nothing
	}

	override fun onSend(record: ProducerRecord<String, String>): ProducerRecord<String, String> {
		record.headers().add(RecordHeader("x-custom-header", false.toString().toByteArray()))

		record.topic().plus("_test")
		println("### TOPIC : ${record.topic()}")

		return record
	}

	override fun onAcknowledgement(metadata: RecordMetadata?, exception: Exception?) {
		// do nothing
	}

	override fun close() {
		// do nothing
	}
}