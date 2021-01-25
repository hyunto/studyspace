package xyz.hyunto.core.mapper

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import xyz.hyunto.core.config.database.MySql1Mapper
import xyz.hyunto.core.model.Action
import xyz.hyunto.core.model.TableName

// language=SQL
@Repository
@MySql1Mapper
interface DualWriteInconsistencyMapper {

	@Insert("""
		INSERT INTO dual_write_inconsistency(
			target_table, 
			target_id, 
			action
		) VALUES (
			#{tableName},
			#{targetId},
			#{action}
		) 
	""")
	fun insert(@Param("tableName") tableName: TableName,
			   @Param("targetId") targetId: Long,
			   @Param("action") action: Action)

}
