package xyz.hyunto.async.model.enums

enum class TableName(
	val value: String,
	val mapperName: String
) {
	USER("user", "UserMapper"),
	GROUP("group", "GroupMapper")
}
