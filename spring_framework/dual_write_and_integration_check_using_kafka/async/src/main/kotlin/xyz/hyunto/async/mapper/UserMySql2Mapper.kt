package xyz.hyunto.async.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import xyz.hyunto.async.config.database.MySql2Mapper
import xyz.hyunto.core.model.User

// language=SQL
@MySql2Mapper
@Repository
interface UserMySql2Mapper {

	@Insert("""
		INSERT INTO user (
			name, 
			age
		) VALUES (
			#{user.name},
			#{user.age}
		)
	""")
	fun insert(@Param("user") user: User)

	@Update("""
		UPDATE user
		SET
			name = #{user.name},
			age = #{age.name}
		WHERE
			id = #{age.id}
	""")
	fun update(@Param("user") user: User)

	@Delete("""
		DELETE FROM user
		WHERE id = #{id}
	""")
	fun delete(@Param("id") id: Long)

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
