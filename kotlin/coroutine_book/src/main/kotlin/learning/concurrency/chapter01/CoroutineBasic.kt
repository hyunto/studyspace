package learning.concurrency.chapter01

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun createCoroutines(amount: Int) = runBlocking {
	val jobs = ArrayList<Job>()
	for (i in 1..amount) {
		jobs += launch {
			println("Started $i in ${Thread.currentThread().name}")
			delay(1000)
			println("Finished $i in ${Thread.currentThread().name}")
		}
	}
	jobs.forEach {
		it.join()
	}
}

suspend fun main(args: Array<String>) {
	println("${Thread.activeCount()} threads active at the start")

	val time = measureTimeMillis {
		createCoroutines(10_000)
	}
	println("${Thread.activeCount()} threads active at the end")
	println("Took $time ms")
}