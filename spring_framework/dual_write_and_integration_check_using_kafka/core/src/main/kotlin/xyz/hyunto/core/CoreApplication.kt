package xyz.hyunto.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class CoreApplication

fun main(args: Array<String>) {
	runApplication<CoreApplication>(*args)
}
