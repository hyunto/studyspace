package xyz.hyunto.core.interceptor.enums

import org.apache.ibatis.mapping.SqlCommandType

enum class Action(
	val value: Int,
	val sqlCommandType: SqlCommandType
) {
	INSERT(100, SqlCommandType.INSERT),
	UPDATE(200, SqlCommandType.UPDATE),
	DELETE(300, SqlCommandType.DELETE);

	companion object {
		private val sqlCommandTypeLookupMap = values().associateBy { it.sqlCommandType }
		fun fromSqlCommandType(sqlCommandType: SqlCommandType) = sqlCommandTypeLookupMap[sqlCommandType]
	}
}
