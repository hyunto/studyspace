package xyz.hyunto.core.interceptor.enums

import xyz.hyunto.core.mapper.GroupMapper
import xyz.hyunto.core.mapper.UserMapper
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
