package xyz.hyunto.async.mapper

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import xyz.hyunto.async.config.database.MySql1Mapper
import xyz.hyunto.async.model.enums.Action
import xyz.hyunto.async.model.enums.TableName

// language=SQL
@Repository
@MySql1Mapper
interface DualWriteInconsistencyMapper {

	@Insert("""
		INSERT INTO dual_write_inconsistency(
			target_table, 
			target_id,
			properties,
			action,
			action_datetime
		) VALUES (
			#{tableName},
			#{targetId},
			#{action}
		) 
	""")
	fun insert(@Param("tableName") tableName: TableName,
			   @Param("targetId") targetId: Long?,
			   @Pa
			   @Param("action") action: Action)

}
