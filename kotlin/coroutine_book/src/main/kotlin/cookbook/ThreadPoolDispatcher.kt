package cookbook

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

/**
 * 코틀린 쿡북 13.4장
 */
fun main() {
	threadPoolAsCoroutineDispatcher()
	shutdownThreadPool()
	dispatcherWithUseBlock()
}

fun threadPoolAsCoroutineDispatcher() = runBlocking {
	val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

	withContext(dispatcher) {
		delay(100L)
		println("threadPoolAsCoroutineDispatcher (${Thread.currentThread().name})")
	}

	dispatcher.close()    // 내부적으로 ExecutorService.shutdown()을 호출
}

fun shutdownThreadPool() = runBlocking {
	val pool = Executors.newFixedThreadPool(10)

	withContext(pool.asCoroutineDispatcher()) {
		delay(100L)
		println("shutdownThreadPool (${Thread.currentThread().name})")
	}

	pool.shutdown()
}

fun dispatcherWithUseBlock() = runBlocking {
	Executors.newFixedThreadPool(10).asCoroutineDispatcher().use {
		withContext(it) {
			delay(100L)
			println("dispatcherWithUseBlock (${Thread.currentThread().name})")
		}
	}
}