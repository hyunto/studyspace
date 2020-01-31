package sandbox

fun main(args: Array<String>) {
	val greetingFunction: (String, Int) -> String = { playerName, numBuildings ->
		val currentYear = 2020
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println(greetingFunction("hyunto", 2))

	// --------------------------------------------------------------------
	val greetingFunction2 = {
		val currentYear = 2020
		"SimVillage 방문을 환영합니다, 촌장님! (copyright $currentYear)"
	}
	println(greetingFunction2())
}