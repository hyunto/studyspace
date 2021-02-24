package xyz.hyunto.async.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository
import xyz.hyunto.async.config.database.MySql1Mapper
import xyz.hyunto.core.model.Group

// language=SQL
@MySql1Mapper
@Repository
interface GroupMySql1Mapper {

	@Insert("""
		INSERT INTO `group`(
			name, 
			description
		) VALUES (
			#{group.name},
			#{group.description}
		)
	""")
	fun insert(@Param("group") group: Group)

	@Update("""
		UPDATE `group`
		SET
			name = #{group.name},
			description = #{group.description}
		WHERE
			id = #{group.id}
	""")
	fun update(@Param("group") group: Group)

	@Delete("""
		DELETE FROM `group`
		WHERE id = #{id}
	""")
	fun delete(@Param("id") id: Long)

	@Select("""
		SELECT
			id,
			name,
			description
		FROM `group`
		WHERE id = #{id}
	""")
	fun selectById(@Param("id") id: Long): Group

}
