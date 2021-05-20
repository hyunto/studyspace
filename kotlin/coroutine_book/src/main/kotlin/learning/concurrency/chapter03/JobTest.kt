package learning.concurrency.chapter03

import kotlinx.coroutines.*
import java.time.ZonedDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * 기본적으로 Job 내부에서 발생한 예외는 생성한 곳까지 전파된다.
 *   - 결과: 5초 뒤 Job 완료를 기다리지 않고 즉시 에러 발생(NotImplementedError)
 */
//fun main(args: Array<String>) = runBlocking {
//	GlobalScope.launch {
//		TODO("Not Implemented!")
//	}
//	delay(5000L)
//}

/**
 * start 옵션으로 CoroutineStart.LAZY를 사용하면 Job 생성시 자동으로 시작되지 않게 할 수 있다.
 *   - 결과: 5초 대기 후 정상종료
 */
//fun main(args: Array<String>) = runBlocking {
//	GlobalScope.launch(start = CoroutineStart.LAZY) {
//		TODO("Not Implemented!")
//	}
//	delay(5000L)
//}

/**
 * start()는 Job 완료를 기다리지 않고 실행하기 때문에 어플리케이션은 즉시 종료한다.
 */
//fun main(args: Array<String>) {
//	val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//		delay(3000L)
//	}
//	job.start()
//}

/**
 * join()은 Job이 완료될 때까지 기다리기 때문에 어플리케이션은 3초 대기 후 종료한다.
 */
//fun main(args: Array<String>) = runBlocking {
//	val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//		delay(3000L)
//	}
//	job.join()
//}

/**
 * "Custom Message" 출력
 */
//@OptIn(InternalCoroutinesApi::class)
//fun main(args: Array<String>) = runBlocking {
//	val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
//		delay(5000L)
//	}
//	job.start()
//	delay(2000L)
//	job.cancel(message = "Custom Message", cause = Exception("Exception Message"))
//
//	val cancellation = job.getCancellationException()
//	println(cancellation.message)
//}

/**
 * CoroutineExceptionHandler를 사용한 예외 처리
 */
//fun main(args: Array<String>) = runBlocking {
//	val exceptionHandler = CoroutineExceptionHandler {
//		_: CoroutineContext, throwable: Throwable ->
//		println("Job cancelled due to ${throwable.message}")
//	}
//	GlobalScope.launch(context = exceptionHandler) {
//		TODO("Not implemented yet!")
//	}
//	delay(2000L)
//}

/**
 * invokeOnCompletion()을 사용한 예외 처리
 */
//fun main(args: Array<String>) = runBlocking {
//	GlobalScope.launch {
//		TODO("Not implemented yet!")
//	}.invokeOnCompletion { cause ->
//		cause?.let {
//			println("Job cancelled due to ${it.message}")
//		}
//	}
//	delay(2000L)
//}