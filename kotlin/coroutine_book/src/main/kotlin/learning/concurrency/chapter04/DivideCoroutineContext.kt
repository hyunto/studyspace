package learning.concurrency.chapter04

import kotlinx.coroutines.*

/**
 * 결합된 코루틴 컨텍스트를 분리
 * myDispatcher가 아닌 기본 디스패처에서 실행된다.
 */
fun main(args: Array<String>) = runBlocking {
	val dispatcher = newSingleThreadContext("myDispatcher")
	val handler = CoroutineExceptionHandler({ context, throwable ->
		println("Error captured in $context")
		println("Message: ${throwable.message}")
	})

	// 컨텍스트 결합
	val combinedContext = dispatcher + handler

	// 컨텍스트에서 하나의 요소 제거
	val tempContext = combinedContext.minusKey(dispatcher.key)

	GlobalScope.launch (tempContext) {
		println("Running in ${Thread.currentThread().name}")
		TODO("Not implemented!")
	}.join()
}