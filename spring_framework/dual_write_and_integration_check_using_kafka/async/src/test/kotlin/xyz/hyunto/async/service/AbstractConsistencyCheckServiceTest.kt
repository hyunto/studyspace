package xyz.hyunto.async.service

import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class AbstractConsistencyCheckServiceTest {

	private var service: AbstractConsistencyCheckService<User>

	@Before
	fun setUp() {
		service = AbstractConsistencyCheckService<User>()
	}
}
