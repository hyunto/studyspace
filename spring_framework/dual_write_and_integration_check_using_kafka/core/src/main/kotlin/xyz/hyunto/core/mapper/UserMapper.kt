package xyz.hyunto.core.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import xyz.hyunto.core.config.database.MySql1Mapper
import xyz.hyunto.core.interceptor.ConsistencyCheckById
import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName
import xyz.hyunto.core.model.User

// language=SQL
@MySql1Mapper
@Repository
interface UserMapper {

	@Insert("""
		INSERT INTO user (
			name, 
			age
		) VALUES (
			#{user.name},
			#{user.age}
		)
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.INSERT, id = "name", type = User::class)
	fun insert(@Param("user") user: User)

	@Insert("""
		INSERT INTO user (
			id,
			name,
			age
		) VALUES (
			#{user.id},
			#{user.name},
			#{user.age}
		)
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.INSERT, id = "id", type = User::class)
	fun insertWithId(@Param("user") user: User)

	@Insert("""
		INSERT INTO user (
			name, 
			age
		) VALUES (
			#{name},
			#{age}
		)
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.INSERT, id = "name", type = Int::class)
	fun insertByValue(@Param("name") name: String, @Param("age") age: Int)

	@Select("""
		SELECT LAST_INSERT_ID()
		FROM user
	""")
	fun getLastInsertedId(): Long

	@Update("""
		UPDATE user
		SET
			name = #{user.name},
			age = #{user.age}
		WHERE
			id = #{user.id}
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.UPDATE, id = "id", type = User::class)
	fun update(@Param("user") user: User)

	@Delete("""
		DELETE FROM user
		WHERE id = #{id}
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.DELETE, id = "id", type = Int::class)
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
