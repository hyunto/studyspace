package xyz.hyunto.core.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import xyz.hyunto.core.config.database.MySql1Mapper
import xyz.hyunto.core.interceptor.*
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
	@DualWriteConsistencyCheck(tableName = TableName.USER, action = Action.INSERT, query = "selectById", params = [
		QueryParam(name = "user", subParams = [
			QuerySubParam(name = "name"),
			QuerySubParam(name = "age", mappingName = "mappingAge")
		])
	])
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
	@DualWriteConsistencyCheck(tableName = TableName.USER, action = Action.INSERT, query = "selectById", params = [
		QueryParam(name = "name"),
		QueryParam(name = "age", mappingName = "mappingAge")
	])
	fun insertByValue(@Param("name") name: String, @Param("age") age: Int)

	@Insert("""<script>
		INSERT INTO user (
			name,
			age
		) VALUES 
		<foreach collection='users' item='user' open='(' separator='), (' close=')'>
			#{user.name},
			#{user.age}
		</foreach>
	</script>""")
	@DualWriteConsistencyCheck(tableName = TableName.USER, action = Action.INSERT, query = "list", params = [
		QueryParam(name = "users", subParams = [
			QuerySubParam(name = "name"),
			QuerySubParam(name = "age", mappingName = "mappingAge")
		])
	])
	fun inserts(@Param("users") users: List<User>)

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
	@DualWriteConsistencyCheck(tableName = TableName.USER, action = Action.UPDATE, query = "selectById", params = [
		QueryParam(name = "user", subParams = [
			QuerySubParam(name = "id"),
			QuerySubParam(name = "name")
		])
	])
	fun update(@Param("user") user: User)

	@Delete("""
		DELETE FROM user
		WHERE id = #{id}
	""")
	@ConsistencyCheckById(tableName = TableName.USER, action = Action.DELETE, id = "id", type = Int::class)
	fun delete(@Param("id") id: Long)

	@Delete("""
		DELETE FROM user
		WHERE name = #{name}
	""")
	@ConsistencyCheckByProperties(tableName = TableName.USER, action = Action.DELETE, properties = ["name"])
	fun deleteByName(@Param("name") name: String)

	@Select("""
		SELECT
			id,
			name,
			age
		FROM user
		WHERE id = #{id}
	""")
	fun selectById(@Param("id") id: Long): User?

	@Select("""<script>
		DELETE FROM user
		WHERE name = #{name}
		AND id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>
			#{id}
		</foreach>
	</script>""")
	@DualWriteConsistencyCheck(tableName = TableName.USER, action = Action.DELETE, query = "selectById", params = [
		QueryParam(name = "name"),
		QueryParam(name = "ids")
	])
	fun deleteByNameAndIds(name: String, ids: List<Long>): List<User>

}
