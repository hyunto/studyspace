package learning.concurrency.chapter01

import kotlinx.coroutines.*

data class UserInfo(
	val id: Int,
	val name: String,
	val age: Int
)

lateinit var user: UserInfo

fun main(args: Array<String>) = runBlocking {
	println("Start asyncGetUserInfo()")
	asyncGetUserInfo(1)
//	delay(1500L)
	println("Finish asyncGetUserInfo()")

	// 초기화되지 않은 user 프로퍼티를 접근하려 해서 에러 발생
	println("User ${user.id} is ${user.name}(${user.age})")
}

fun asyncGetUserInfo(id: Int) = GlobalScope.async {
	println("Waiting...")
	delay(1000L)
	println("Done...!")

	user = UserInfo(id = id, name = "hyunto", age = 34)
}