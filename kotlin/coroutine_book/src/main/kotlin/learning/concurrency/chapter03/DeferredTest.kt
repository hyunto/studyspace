package learning.concurrency.chapter03

import kotlinx.coroutines.*

suspend fun main(args: Array<String>) {
	asyncGetMessage()
	asyncGetMessage2()

//	exceptionHandling1()
//	exceptionHandling2()
	exceptionHandling3()
}

private fun asyncGetMessage() = runBlocking {
	val task = GlobalScope.async {
		"Hello, World!"
	}
	println(task.await())
}

private fun asyncGetMessage2() = runBlocking {
	val task = CompletableDeferred<String>("Hello, World!")
	println(task.await())
}

/**
 * Deferred가 예외를 전파하도록 하는 방법
 */
private fun exceptionHandling1(): Nothing = runBlocking {
	val deferred = GlobalScope.async {
		TODO("Not implemented yet!")
	}
	deferred.await()
}

/**
 * try/catch로 예외를 처리하는 방법
 */
private fun exceptionHandling2() = runBlocking {
	val deferred = GlobalScope.async {
		TODO("Not implemented yet!")
	}

	try {
		deferred.await()
	} catch (throwable: Throwable) {
		println("Deferred cancelled due to ${throwable.message}")
	}
}

/**
 * ExceptionHandler로 예외를 처리하는 방법
 */
private fun exceptionHandling3(): Nothing = runBlocking {
	val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
		println("Deferred cancelled due to ${throwable.message}")
	}
	val deferred = GlobalScope.async(context = exceptionHandler) {
		TODO("Not implemented yet!")
	}

	deferred.await()
}