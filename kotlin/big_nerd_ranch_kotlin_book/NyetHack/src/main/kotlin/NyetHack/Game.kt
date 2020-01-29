package NyetHack

fun main(args: Array<String>) {
	val name = "마르티나"
	var healthPoints = 100
	healthPoints -= 11

	val isBlessed = true
	val isImmortal = false

	// 아우라
	val auraVisible = isBlessed && healthPoints > 50 || isImmortal
	val auraColor = if(auraVisible) "GREEN" else "NONE"
	println(auraColor)

	val healthStatus = if (healthPoints == 100) {
		"최상의 상태임!"
	} else if (healthPoints >= 90){
		"약간의 찰과상만 있음"
	} else if (healthPoints >= 75) {
		if (isBlessed) {
			"경미한 상처가 있지만 빨리 치유됨!"
		} else {
			"경미한 상처만 있음"
		}
	} else if (healthPoints >= 15) {
		"많이 다친 것 같음"
	} else {
		"최악의 상태임!"
	}

	// 플레이어의 상태 출력
	println(name + " " + healthStatus)
}