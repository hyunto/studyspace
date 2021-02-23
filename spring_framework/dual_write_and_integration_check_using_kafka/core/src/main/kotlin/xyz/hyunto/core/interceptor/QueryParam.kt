package xyz.hyunto.core.interceptor

import kotlin.reflect.KClass

annotation class QueryParam(
	val name: String,
	val mappingName: String = "",
	val subParams: Array<QuerySubParam> = []
)
