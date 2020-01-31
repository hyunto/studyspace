package sandbox

fun main(args: Array<String>) {
	val greetingFunction: (String, Int) -> String = { playerName, numBuildings ->
		val currentYear = 2020
		println("*** greetingFunction ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println(greetingFunction("hyunto", 2))
	println()

	// --------------------------------------------------------------------
	val greetingFunction2 = {
		val currentYear = 2020
		println("*** greetingFunction2 ***")
		"SimVillage 방문을 환영합니다, 촌장님! (copyright $currentYear)"
	}
	println(greetingFunction2())
	println()

	// --------------------------------------------------------------------
	val greetingFunction3 = { playerName: String, numBuildings: Int ->
		val currentYear = 2020
		println("*** greetingFunction3 ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println(greetingFunction3("hyunto", 5))
	println()

	// --------------------------------------------------------------------
	val greetingFunction4 = { playerName: String, numBuildings: Int ->
		val currentYear = 2020
		println("*** greetingFunction4 ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	runSimulation("hyunto", greetingFunction4)
	println()

	// --------------------------------------------------------------------
	runSimulation("hyunto") { playerName, numBuildings ->
		val currentYear = 2020
		println("*** 단축 문법 ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println()
}

fun runSimulation(playerName: String, greetingFunction: (String, Int) -> String) {
	val numBuildings = (1..3).shuffled().last()
	println(greetingFunction(playerName, numBuildings))
}