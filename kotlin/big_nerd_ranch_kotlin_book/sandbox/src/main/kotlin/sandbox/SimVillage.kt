package sandbox

fun main(args: Array<String>) {
	val greetingFunction: (String) -> String = {
		val currentYear = 2020
		"SimVillage 방문을 환영합니다, $it 님! (copyright $currentYear)"
	}
	println(greetingFunction("hyunto"))
}