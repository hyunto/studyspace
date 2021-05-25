package learning.concurrency.chapter04

import kotlinx.coroutines.runBlocking

data class ProfileA(
	val id: Long,
	val name: String,
	val age: Int
)

interface ProfileServiceRepositoryA {
	suspend fun fetchByName(name: String): ProfileA
	suspend fun fetchById(id: Long): ProfileA
}

class ProfileServiceClientA: ProfileServiceRepositoryA {
	override suspend fun fetchByName(name: String): ProfileA {
		return ProfileA(1, name, 34)
	}

	override suspend fun fetchById(id: Long): ProfileA {
		return ProfileA(id, "hyunto", 34)
	}
}

internal fun main(args: Array<String>) = runBlocking {
	val client = ProfileServiceClientA()
	val profile = client.fetchById(12)
	println(profile)
}