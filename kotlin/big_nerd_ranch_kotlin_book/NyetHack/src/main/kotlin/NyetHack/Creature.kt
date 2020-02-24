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