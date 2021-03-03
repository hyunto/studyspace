package xyz.hyunto.core.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import xyz.hyunto.core.interceptor.DatabaseTypeHolder
import xyz.hyunto.core.mapper.UserMapper
import xyz.hyunto.core.model.User

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
	val userMapper: UserMapper
) {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun insert(@RequestBody user: User) {
		userMapper.insert(user)
	}

	@PostMapping("/by-value")
	@ResponseStatus(HttpStatus.CREATED)
	fun insertByValue(@RequestBody user: User) {
		userMapper.insertByValue(user.name, user.age)
	}

	@PostMapping("/multiple")
	@ResponseStatus(HttpStatus.CREATED)
	fun inserts(@RequestBody users: List<User>) {
		userMapper.inserts(users)
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
	fun deleteByName(@RequestParam(required = false) name: String) {
		if (name == null) {
			throw HttpClientErrorException(HttpStatus.BAD_REQUEST)
		}
		val results = userMapper.deleteByName(name)
		println(results)
	}

	@GetMapping("/{id}")
	fun get(@PathVariable id: Long): User {
//		println("### Multiple Datasource Get Test ###")
//		DatabaseTypeHolder.setMySql1()
//		println(userMapper.selectById(id))
//		DatabaseTypeHolder.setMySql2()
//		println(userMapper.selectById(id))

		return userMapper.selectById(id) ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND)
	}

	@DeleteMapping("/by-name-and-ids")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun deleteByNameAndIds(@RequestBody request: NameAndIdsRequest){
		with(request) {
			userMapper.deleteByNameAndIds(name, ids)
		}
	}

	data class NameAndIdsRequest(
		val name: String,
		val ids: List<Long>
	)

}
