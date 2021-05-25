package learning.concurrency.chapter04

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 다음의 메시지를 출력하고 정상 종료된다.
 *
 * still running
 * still running
 * cancelled, will end now
 * delay completed, bye bye
 * Took 7527 ms
 */
fun main(args: Array<String>) = runBlocking {
	val duration = measureTimeMillis {
		val job = launch {
			try {
				while (isActive) {
					delay(1000)
					println("still running")
				}
			} finally {
				// NonCancellable 컨텍스트를 사용하지 않으면 delay(5000)은 실행되지 않고 즉시 종료된다.
				withContext(NonCancellable) {
					println("cancelled, will end now")
					delay(5000)
					println("delay completed, bye bye")
				}
			}
		}
		delay(2500)
		job.cancelAndJoin()
	}
	println("Took $duration ms")
}