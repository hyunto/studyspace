package xyz.hyunto.core.config.controller

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
	val kafkaTemplate: KafkaTemplate<String, String>
) {

	@GetMapping
	fun test() {
		println("Hello, world! - TestController.test()")
		kafkaTemplate.send("integrity_check", "test","Hello, world! - sending message via kafka")
	}
}
