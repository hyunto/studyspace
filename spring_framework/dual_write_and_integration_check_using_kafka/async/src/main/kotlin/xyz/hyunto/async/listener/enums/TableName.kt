package xyz.hyunto.async.listener.enums

import xyz.hyunto.async.mapper.GroupMapper
import xyz.hyunto.async.mapper.UserMapper
import kotlin.reflect.KClass

enum class TableName(
	val checkerName: String,
	val mapperName: String,
	val mapper: KClass<*>
) {
	USER("UserDualWriteCheckBO", "UserMapper", UserMapper::class),
	GROUP("GroupDualWriteCheckBO", "GroupMapper", GroupMapper::class)
}
