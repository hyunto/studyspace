package xyz.hyunto.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import xyz.hyunto.core.model.User

@SpringBootTest
class CoreApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun diffTest() {
		val user1 = User(id = 1L, name = "hyunto", age = 20)
		val user2 = User(id = 1L, name = "hyunto", age = 20)

		Assertions.assertTrue(user1 == user2)

		val user3 = User(id = 1L, name = "hyunto3", age = 20)
		val user4 = User(id = 1L, name = "hyunto4", age = 20)

		Assertions.assertFalse(user3 == user4)
	}

}
