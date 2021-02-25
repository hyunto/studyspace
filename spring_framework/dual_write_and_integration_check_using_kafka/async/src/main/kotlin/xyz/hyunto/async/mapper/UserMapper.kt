package xyz.hyunto.async.mapper

import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import xyz.hyunto.async.model.User

// language=SQL
@Repository
interface UserMapper {

	@Select("""
		SELECT
			id,
			name,
			age
		FROM user
		WHERE id = #{id}
	""")
	fun selectById(@Param("id") id: Long): User?

}
