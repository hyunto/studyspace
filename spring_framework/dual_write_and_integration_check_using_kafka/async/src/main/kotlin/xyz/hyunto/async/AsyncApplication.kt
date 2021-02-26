package xyz.hyunto.async

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
class AsyncApplication

fun main(args: Array<String>) {
	runApplication<AsyncApplication>(*args)
}
