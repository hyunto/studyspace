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
	// 단축 문법
	runSimulation("hyunto") { playerName, numBuildings ->
		val currentYear = 2020
		println("*** 단축 문법 ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println()

	// --------------------------------------------------------------------
	// inline 키워드
	runSimulationInline("hyunto") { playerName, numBuildings ->
		val currentYear = 2020
		println("*** inline 키워드 ***")
		println("$numBuildings 채의 건물이 추가된")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println()

	// --------------------------------------------------------------------
	// 함수 참조
	println("*** 함수 참조 ***")
	runSimulationFunctionReference("hyunto", ::printConstructionCost) { playerName, numBuildings ->
		val currentYear = 2020
		println("$numBuildings 채의 건물이 추가됨")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
	println()

	// --------------------------------------------------------------------
	// 함수를 반환하는 함수
	println("*** 함수를 반환하는 함수 ***")
	runSimulationReteruningFunction()
}

fun runSimulation(playerName: String, greetingFunction: (String, Int) -> String) {
	val numBuildings = (1..3).shuffled().last()
	println(greetingFunction(playerName, numBuildings))
}

inline fun runSimulationInline(playerName: String, greetingFunction: (String, Int) -> String) {
	val numBuildings = (1..3).shuffled().last()
	println(greetingFunction(playerName, numBuildings))
}

inline fun runSimulationFunctionReference(playerName: String, costPrinter: (Int) -> Unit, greetingFunction: (String, Int) -> String) {
	val numBuildings = (1..3).shuffled().last()
	costPrinter(numBuildings)
	println(greetingFunction(playerName, numBuildings))
}

fun runSimulationReteruningFunction() {
	val greetingFunction = configureGreetingFunction()

	// 클로저로 인해 configureGreetingFunction()에서 반환된 람다식 밖에 있는 numBuildings 값을 계속해서 참조할 수 있다.
	println(greetingFunction("hyunto"))		// numBuildings 값 : 6
	println(greetingFunction("hyunto"))		// numBuildings 값 : 7
	println(greetingFunction("hyunto"))		// numBuildings 값 : 8
}

fun printConstructionCost(numBuildings: Int) {
	val cost = 500
	println("건축 비용: ${cost * numBuildings}")
}

fun configureGreetingFunction(): (String) -> String {
	val structureType = "병원"
	var numBuildings = 5
	return { playerName: String ->
		val currentYear = 2020
		numBuildings += 1
		println("$numBuildings 채의 $structureType 이 추가됨")
		"SimVillage 방문을 환영합니다, $playerName 님! (copyright $currentYear)"
	}
}