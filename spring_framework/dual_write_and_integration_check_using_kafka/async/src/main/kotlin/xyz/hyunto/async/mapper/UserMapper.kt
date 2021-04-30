package xyz.hyunto.async.mapper

import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
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
	fun selectById(id: Long): User?

	@Select("""
		SELECT
			id,
			name,
			age
		FROM user 
		WHERE name = #{name}
		LIMIT 1
	""")
	fun selectByName(name: String): User?

	@Select("""
		SELECT 
			id,
			name,
			age
		FROM user
		WHERE name = #{name}
		AND age = #{age}
	""")
	fun listByNameAndAge(name: String, age: Int): List<User>

	@Select("""<script>
		SELECT 
			id,
			name,
			age
		FROM user
		WHERE name = #{name}
		AND id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>
			#{id}
		</foreach>
	</script>""")
	fun listByNameAndIds(name: String, ids: List<Long>): List<User>

	@Select("""<script>
		SELECT
			id,
			name,
			age
		FROM user
		WHERE id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>
			#{id}
		</foreach>
	</script>""")
	fun listByIds(ids: List<Long>): List<User>

	@Select("""
		SELECT id, name, age
		FROM user
		WHERE name = #{name}
	""")
	fun listByName(name: String): List<User>

}
