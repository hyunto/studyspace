package NyetHack.challenge

fun main(args: Array<String>) {
	val name = "마르티나"
	var healthPoints = 100
	healthPoints -= 11

	val isBlessed = true
	val isImmortal = false

	val karma = (Math.pow(Math.random(), (110 - healthPoints) / 100.0) * 20).toInt()

	// 아우라
	val auraVisible = isBlessed && healthPoints > 50 || isImmortal
	val auraColor = when(karma) {
		in 0..5 -> "RED"
		in 6..10 -> "ORANGE"
		in 11..15 -> "PURPLE"
		in 16..20 -> "GREEN"
		else -> "NONE"
	}

	val healthStatus = when(healthPoints) {
		100 -> "최상의 상태임!"
		in 90..99 -> "약간의 찰과상만 있음"
		in 75..89 -> if (isBlessed) {
						"경미한 상처가 있지만 빨리 치유됨!"
					} else {
						"경미한 상처만 있음"
					}
		in 15..74 -> "많이 다친 것 같음"
		else -> "최악의 상태임!"
	}

	// 플레이어의 상태 출력
	println((if (auraVisible) "(Aura: $auraColor) " else "")
		+ "(Blessed: ${if (isBlessed) "YES" else "NO"})")
	println("$name $healthStatus")

	val statusFormatString = String.format("\n(HP: %d)(Aura: %s) -> %s %s", healthPoints, auraColor, name, healthStatus)
	println(statusFormatString)
}