package xyz.hyunto.core.mapper

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository
import xyz.hyunto.core.interceptor.enums.Action
import xyz.hyunto.core.interceptor.enums.TableName

// language=SQL
@Repository
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
