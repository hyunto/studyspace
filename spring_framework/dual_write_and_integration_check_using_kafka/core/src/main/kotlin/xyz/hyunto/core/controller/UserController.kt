package xyz.hyunto.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import xyz.hyunto.core.mapper.GroupJavaMapper
import xyz.hyunto.core.mapper.UserMapper
import xyz.hyunto.core.model.User

@RestController
@RequestMapping("/users")
class UserController(
	val userMapper: UserMapper
) {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun insert(@RequestBody user: User) {
//		userMapper.insert(user)
		userMapper.insertByValue(user.name, user.age)
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	fun update(@PathVariable id: Long, @RequestBody user: User) {
		userMapper.update(user.apply { this.id = id })
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable id: Long) {
		userMapper.delete(id)
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun deleteBy(@RequestParam(required = false) name: String) {
		if (name == null) {
			throw HttpClientErrorException(HttpStatus.BAD_REQUEST)
		}
		userMapper.deleteByName(name)
	}

	@GetMapping("/{id}")
	fun get(@PathVariable id: Long): User {
		return userMapper.selectById(id) ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND)
	}

}
