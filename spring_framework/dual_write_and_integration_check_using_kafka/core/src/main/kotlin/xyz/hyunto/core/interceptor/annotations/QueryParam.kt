package xyz.hyunto.core.interceptor.annotations

annotation class QueryParam(
	val name: String,
	val subQueryParams: Array<SubQueryParam> = []
)
