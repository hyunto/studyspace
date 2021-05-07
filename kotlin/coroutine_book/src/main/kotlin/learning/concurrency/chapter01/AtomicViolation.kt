package learning.concurrency.chapter01

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

var counter = 0

fun main(args: Array<String>) = runBlocking {
	val workerA = asyncIncrement(2000)
	val workerB = asyncIncrement(1000)
	workerA.await()
	workerB.await()

	// counter의 예상 결과는 3000이지만, 그보다 적은 값이 나온다.
	println("Counter $counter")
}

fun asyncIncrement(by: Int) = GlobalScope.async {
	for (i in 0 until by) {
		counter++	// 원자적이지 않은 작업
	}
}