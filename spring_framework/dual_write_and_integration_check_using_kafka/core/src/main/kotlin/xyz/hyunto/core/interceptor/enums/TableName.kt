package xyz.hyunto.core.interceptor.enums

enum class TableName(
	val checkerName: String
) {
	USER("UserDualWriteCheckBO"),
	GROUP("GroupDualWriteCheckBO")
}
