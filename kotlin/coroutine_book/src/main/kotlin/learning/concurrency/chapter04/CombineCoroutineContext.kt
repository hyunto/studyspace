package learning.concurrency.chapter04

import kotlinx.coroutines.*

/**
 * 단일 스레드 디스패처와 예외처리가 결합된 코루틴 컨텍스트
 */
fun main(args: Array<String>) = runBlocking {
	val dispatcher = newSingleThreadContext("myDispatcher")
	val handler = CoroutineExceptionHandler({ context, throwable ->
		println("Error captured in $context")
		println("Message: ${throwable.message}")
	})
	val combinedContext = dispatcher + handler
	GlobalScope.launch (combinedContext) {
		println("Running in ${Thread.currentThread().name}")
		TODO("Not implemented!")
	}.join()
}