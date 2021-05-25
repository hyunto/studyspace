package learning.concurrency.chapter04

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(args: Array<String>) = runBlocking {
	val dispatcher = newSingleThreadContext("myDispatcher")
	val name = withContext(dispatcher) {
		println("Running in ${Thread.currentThread().name}")
		"hyunto"
	}
	println("User: $name")
}