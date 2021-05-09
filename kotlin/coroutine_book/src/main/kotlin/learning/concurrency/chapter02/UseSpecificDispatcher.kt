package learning.concurrency.chapter02

import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
	runInDefaultDispatcher()
	runInSpecificDispatcher()
}

private fun printCurrentThread() {
	println("Running in thread [${Thread.currentThread().name}]")
}

/**
 * 출력 : Running in thread [main]
 */
private fun runInDefaultDispatcher() = runBlocking {
	val task = launch {
		printCurrentThread()
	}
	task.join()
}

/**
 * 출력 : Running in thread [ServiceCall]
 */
private fun runInSpecificDispatcher() = runBlocking {
	val dispatcher = newSingleThreadContext(name = "ServiceCall")
	val task = launch(dispatcher) {
		printCurrentThread()
	}
	task.join()
}