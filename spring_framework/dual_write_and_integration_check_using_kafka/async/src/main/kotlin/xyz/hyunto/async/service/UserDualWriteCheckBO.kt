package xyz.hyunto.async.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.hyunto.async.mapper.UserMapper

@Service("UserDualWriteCheckBO")
class UserDualWriteCheckBO @Autowired constructor(
	val userMapper: UserMapper
) : AbstractDualWriteCheck() {

	override fun select(queryName: String, params: List<Any?>): Any? {
		return getMethod(userMapper, queryName).call(userMapper, *params.toTypedArray())
	}

}
