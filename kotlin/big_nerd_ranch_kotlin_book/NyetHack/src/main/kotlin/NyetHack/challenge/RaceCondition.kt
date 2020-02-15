package NyetHack.challenge

class Weapon(val name: String)

class Player {
	val weapon: Weapon? = Weapon("Ebony Kris")

	fun printWeaponName() {
		// Race Condition 발생 가능성 있음
		// if문에서 weapon이 null이 아님을 체크했더라도
		// 스마트 캐스팅이 되기 전에 weapon이 null이 되는 경우가 발생할 여지가 있다.
//		if (weapon != null) {
//			println(weapon.name)
//		}

		// 해결방법
		weapon?.also {
			println(it.name)
		}
	}
}

fun main(args: Array<String>) {
	Player().printWeaponName()
}