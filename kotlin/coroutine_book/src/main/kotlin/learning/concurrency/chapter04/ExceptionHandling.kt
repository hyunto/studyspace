package learning.concurrency.chapter04

import kotlinx.coroutines.*

/**
 * 다음의 메시지를 출력한 후 정상 종료된다.
 *
 * Error captured in [learning.concurrency.chapter04.ExceptionHandlingKt$main$1$invokeSuspend$$inlined$CoroutineExceptionHandler$1@7fb4a89f, StandaloneCoroutine{Cancelling}@3703686d, Dispatchers.Default]
 * Message: An operation is not implemented: Not implemented yet!
 */
fun main(args: Array<String>) = runBlocking {
	val handler = CoroutineExceptionHandler({ context, throwable ->
		println("Error captured in $context")
		println("Message: ${throwable.message}")
	})

	GlobalScope.launch(handler) {
		TODO("Not implemented yet!")
	}

	delay(1000L)
}