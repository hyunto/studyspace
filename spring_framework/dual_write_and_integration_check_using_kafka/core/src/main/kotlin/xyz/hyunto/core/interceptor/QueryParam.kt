package xyz.hyunto.core.interceptor

import kotlin.reflect.KClass

annotation class QueryParam(
	val name: String,
	val isCollection: Boolean = false,
	val subParams: Array<QuerySubParam> = []
)
