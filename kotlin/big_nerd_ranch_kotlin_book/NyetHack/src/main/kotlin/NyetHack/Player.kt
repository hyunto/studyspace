package NyetHack

import NyetHack.extensions.random as randomizer
import java.io.File

class Player(_name: String,
			 override var healthPoints: Int = 100,
			 val isBlessed: Boolean,
			 private val isImmortal: Boolean) : Fightable {

	var name = _name
		get() = "${field.capitalize()} of $hometown"
		set(value) {
			field = value.trim()
		}

	val hometown by lazy { selectHometown() }
	var currentPosition = Coordinate(0, 0)

	override val diceCount = 3
	override val diceSides = 6

	override fun attack(opponent: Fightable): Int {
		val damageDealt = if (isBlessed) {
			damageRoll * 2
		} else {
			damageRoll
		}
		opponent.healthPoints -= damageDealt
		return damageDealt
	}

	private fun selectHometown() = File("data/towns.txt")
		.readText()
		.split("\n")
		.randomizer()

	constructor(name: String) : this(name,
		isBlessed = true,
		isImmortal = false) {
		if (name.toLowerCase() == "link") healthPoints = 80
	}

	fun castFireball(numFireballs: Int = 2) = println("한 덩어리의 파이어볼이 나타난다. (x$numFireballs)")

	fun auraColor(): String {
		val auraVisible = isBlessed && healthPoints > 50 || isImmortal
		return if (auraVisible) "GREEN" else "NONE"
	}

	fun formatHealthStatus() =
		when (healthPoints) {
			100 -> "최상의 상태임!"
			in 90..99 -> "약간의 찰과상만 있음"
			in 75..89 -> if (isBlessed) {
				"경미한 상처가 있지만 빨리 치유됨!"
			} else {
				"경미한 상처만 있음"
			}
			in 15..74 -> "많이 다친 것 같음"
			in 0..14 -> "최악의 상태임!"
			else -> "error"
		}
}