package xyz.hyunto.async.listener.enums

import xyz.hyunto.async.mapper.GroupMapper
import xyz.hyunto.async.mapper.UserMapper
import kotlin.reflect.KClass

enum class TableName(
	val mapper: KClass<*>,
	val value: String
) {
	USER(UserMapper::class, "user"),
	GROUP(GroupMapper::class, "group");

	companion object {
		private val mapperLookup = values().associateBy { it.mapper }
		fun fromMapperClass(clazz: KClass<*>) = mapperLookup[clazz]
	}
}
