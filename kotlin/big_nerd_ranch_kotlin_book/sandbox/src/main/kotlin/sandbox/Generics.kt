package sandbox

class LootBox<T>(item: T) {
	var open = false
	private var loot: T = item

	fun fetch(): T? {
		return loot.takeIf { open }
	}
}

class Fedora(val name: String, val value: Int)

class Coin(val value: Int)

fun main(args: Array<String>) {
	val lootBoxOne: LootBox<Fedora> = LootBox(Fedora("평범한 중절모", 15))
	val lootBoxTwo: LootBox<Coin> = LootBox(Coin(15))

	lootBoxOne.open = true
	lootBoxOne.fetch()?.run {
		println("$name 를 LootBox에서 꺼냈습니다!")
	}
}