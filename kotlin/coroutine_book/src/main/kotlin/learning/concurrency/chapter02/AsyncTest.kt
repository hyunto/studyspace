package learning.concurrency.chapter02

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

private fun doSomething() {
	throw UnsupportedOperationException("Can't do")
}

/**
 * async()의 결과를 처리하지 않은 경우
 * 에러 및 중단 없이 "Completed" 출력
 */
//fun main(args: Array<String>) = runBlocking {
//	val task = GlobalScope.async {
//		doSomething()
//	}
//	task.join()
//	println("Completed")
//}

/**
 * isCancelled로 async() 내부에서 발생한 에러를 처리한 경우
 * "Error with message: java.lang.UnsupportedOperationException: Can't do" 출력
 */
//@OptIn(InternalCoroutinesApi::class)
//fun main(args: Array<String>) = runBlocking {
//	val task = GlobalScope.async {
//		doSomething()
//	}
//	task.join()
//	if (task.isCancelled) {
//		val exception = task.getCancellationException()
//		println("Error with message: ${exception.cause}")
//	} else {
//		println("Completed")
//	}
//}

/**
 * 예외를 전파하기 위해 Deferred에서 await() 호출한 경우
 * "Exception in thread "main" java.lang.UnsupportedOperationException: Can't do" 출력 후 애플리케이션 비정상 종료
 */
fun main(args: Array<String>) = runBlocking {
	val task = GlobalScope.async {
		doSomething()
	}
	task.await()
	println("Completed")
}

