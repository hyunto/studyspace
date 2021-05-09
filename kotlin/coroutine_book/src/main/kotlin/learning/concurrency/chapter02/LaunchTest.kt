package learning.concurrency.chapter02

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun doSomething() {
	throw UnsupportedOperationException("Can't do")
}

/**
 * "Exception in thread "DefaultDispatcher-worker-1" java.lang.UnsupportedOperationException: Can't do" 출력 후
 * 이어서 main() 실행 (애플리케이션 정상 종료)
 */
fun main(args: Array<String>) = runBlocking {
	val task = GlobalScope.launch {
		doSomething()
	}
	task.join()
	println("Completed")
}