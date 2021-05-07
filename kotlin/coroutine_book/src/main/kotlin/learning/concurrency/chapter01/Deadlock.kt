package learning.concurrency.chapter01

import kotlinx.coroutines.*

lateinit var jobA: Job
lateinit var jobB: Job

fun main(args: Array<String>) = runBlocking {
	jobA = GlobalScope.launch {
		println("jobA delay 1000ms")
		delay(1000L)

		println("wait for jobB to finish")
		jobB.join()
	}
	jobB = GlobalScope.launch {
		jobA.join()
	}

	println("wait for jobA to finish")
	jobA.join()

	// jobA는 jobB를 기다리고, jobB는 jobA를 기다려서 Finish는 출력되지 않는다.
	println("Finish")
}
