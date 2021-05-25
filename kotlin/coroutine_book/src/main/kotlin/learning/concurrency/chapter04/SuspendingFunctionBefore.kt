package learning.concurrency.chapter04

import kotlinx.coroutines.*

data class ProfileB(
	val id: Long,
	val name: String,
	val age: Int
)

interface ProfileServiceRepositoryB {
	fun asyncFetchByName(name: String): Deferred<ProfileB>
	fun asyncFetchById(id: Long): Deferred<ProfileB>
}

class ProfileServiceClientB: ProfileServiceRepositoryB {
	override fun asyncFetchByName(name: String): Deferred<ProfileB> = GlobalScope.async {
		ProfileB(1, name, 34)
	}

	override fun asyncFetchById(id: Long): Deferred<ProfileB> = GlobalScope.async {
		ProfileB(id, "hyunto", 34)
	}
}

internal fun main(args: Array<String>) = runBlocking {
	val client = ProfileServiceClientB()
	val profile = client.asyncFetchById(12).await()
	println(profile)
}