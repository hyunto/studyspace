package NyetHack

import java.util.*

interface Fightable {
	var healthPoints: Int
	val diceCount: Int		// 주사위 개수
	val diceSides: Int		// 주사위 면의 수
	val damageRoll: Int		// 주사위를 굴려서 나온 수의 합계
		get() = (0 until diceCount).map{
			Random().nextInt(diceSides) + 1
		}.sum()

	fun attack(opponent: Fightable): Int
}

abstract class Monster(val name: String,
					   val description: String,
					   override var healthPoints: Int) : Fightable {
	override fun attack(opponent: Fightable): Int {
		val damageDealth = damageRoll
		opponent.healthPoints -= damageDealth
		return damageDealth
	}
}

class Goblin(name: String = "Goblin",
			 description: String = "추하게 생긴 고블린",
			 healthPoints: Int = 30) : Monster(name, description, healthPoints) {
	override val diceCount = 2
	override val diceSides = 8
}