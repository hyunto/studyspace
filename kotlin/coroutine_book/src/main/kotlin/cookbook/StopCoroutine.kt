package cookbook

import kotlinx.coroutines.*

fun main() {
	stopJob()
	stopUsingWithTimeoutOrNull()
	stopUsingWithTimeout()
}

fun stopJob() = runBlocking {
	val job = launch {
		repeat(100) {
			println("stopJob: I'm waiting $it...")
			delay(1000L)
		}
	}

	delay(5000L)
	println("main: That's enough waiting")

	// job.cancelAndJoin()으로 한번에 호출 가능
	job.cancel()
	job.join()

	println("main: Done")
}

fun stopUsingWithTimeout() = runBlocking {
	val result = withTimeout(5000L) {
		repeat(100) {
			println("stopUsingWithTimeout: I'm waiting $it...")
			delay(1000L)
		}
	}
	println("stopUsingWithTimeout result: $result")
}

fun stopUsingWithTimeoutOrNull() = runBlocking {
	val result = withTimeoutOrNull(5000L) {
		repeat(100) {
			println("stopUsingWithTimeoutOrNull: I'm waiting $it...")
			delay(1000L)
		}
	}
	println("stopUsingWithTimeoutOrNull result: $result")
}