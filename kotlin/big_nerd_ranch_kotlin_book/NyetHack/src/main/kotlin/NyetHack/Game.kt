package NyetHack

fun main(args: Array<String>) {
	Game.play()

	performCombat()
	performCombat("로우")
	performCombat("슬라임", true)
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

object Game {

	private val player = Player("Martina")
	private var currentRoom = TownSquare()

	init {
		println("방문을 환영합니다.")
		player.castFireball()
	}

	fun play() {
		while (true) {
			println(currentRoom.description())
			println(currentRoom.load())

			// 플레이어의 상태 출력
			printPlayerStatus(player)

			print("> 명령을 입력하세요: ")
			println("최근 명령: ${readLine()}")
		}
	}

	private fun printPlayerStatus(player: Player) {
		println("(Aura: ${player.auraColor()}) " +
				"(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
		println("${player.name} ${player.formatHealthStatus()}")
	}

}