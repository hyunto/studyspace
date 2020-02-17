package NyetHack

fun main(args: Array<String>) {
//	val player = Player("Martina", 89, isBlessed = true, isImmortal = false)
	val player = Player("Martina")
	player.name = "veronica    "
	player.castFireball()

	// 플레이어의 상태 출력
	printPlayerStatus(player)

	performCombat()
	performCombat("로우")
	performCombat("슬라임", true)
}

private fun printPlayerStatus(player: Player) {
	println("(Aura: ${player.auraColor()}) (Blessed: ${if (player.isBlessed) "YES" else "NO"})")
	println("${player.name} ${player.formatHealthStatus()}")
}

fun performCombat() {
	println("적군이 없다!")
}

fun performCombat(enemyName: String) {
	println("$enemyName 과 전투를 시작함")
}

fun performCombat(enemyName: String, isBlessed: Boolean) {
	if (isBlessed) {
		println("$enemyName 과 전투를 시작함. 축복을 받음!")
	} else {
		println("$enemyName 과 전투를 시작함")
	}
}