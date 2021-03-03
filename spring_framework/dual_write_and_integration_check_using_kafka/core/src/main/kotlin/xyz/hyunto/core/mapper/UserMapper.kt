package xyz.hyunto.core.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import xyz.hyunto.core.interceptor.annotations.ConsistencyBulkCheck
import xyz.hyunto.core.interceptor.annotations.ConsistencyCheck
import xyz.hyunto.core.interceptor.annotations.QueryParam
import xyz.hyunto.core.interceptor.annotations.SubQueryParam
import xyz.hyunto.core.interceptor.enums.TableName
import xyz.hyunto.core.model.User

// language=SQL
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
	@ConsistencyCheck(tableName = TableName.USER, queryName = "listByNameAndAge", queryParams = [
		QueryParam(name = "user", subQueryParams = [
			SubQueryParam(name = "name"),
			SubQueryParam(name = "age")
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
	@ConsistencyCheck(tableName = TableName.USER, queryName = "listByNameAndAge", queryParams = [
		QueryParam(name = "name"),
		QueryParam(name = "age")
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
	@ConsistencyBulkCheck(tableName = TableName.USER, queryName = "listByNameAndAge", queryParam = QueryParam(name = "users", subQueryParams = [
		SubQueryParam(name = "name"), SubQueryParam(name = "age")
	]))
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
	@ConsistencyCheck(tableName = TableName.USER, queryName = "selectById", queryParams = [
		QueryParam(name = "user", subQueryParams = [
			SubQueryParam(name = "id")
		])
	])
	fun update(@Param("user") user: User)

	@Delete("""
		DELETE FROM user
		WHERE id = #{id}
	""")
	fun delete(@Param("id") id: Long)

	@Delete("""
		DELETE FROM user
		WHERE name = #{name}
	""")
	@ConsistencyCheck(tableName = TableName.USER, queryName = "listByName", queryParams = [
		QueryParam(name = "name")
	])
	fun deleteByName(@Param("name") name: String)

	@Select("""
		SELECT id, name, age
		FROM user
		WHERE id = #{id}
	""")
	fun selectById(@Param("id") id: Long): User?

	@Delete("""<script>
		DELETE FROM user
		WHERE name = #{name}
		AND id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>
			#{id}
		</foreach>
	</script>""")
	@ConsistencyCheck(tableName = TableName.USER, queryName = "listByNameAndIds", queryParams = [
		QueryParam(name = "name"),
		QueryParam(name = "ids")
	])
	fun deleteByNameAndIds(name: String, ids: List<Long>)

}
