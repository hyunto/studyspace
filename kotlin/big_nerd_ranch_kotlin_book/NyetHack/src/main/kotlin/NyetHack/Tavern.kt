package NyetHack

import java.io.File
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10

val patronList: MutableList<String> = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt")
	.readText()
	.split("\n")
val patronGold = mutableMapOf<String, Double>()

private fun <T> Iterable<T>.random(): T = this.shuffled().first()

fun main(args: Array<String>) {
	val isChapter5 = false
	val isChapter7 = false
	val isChapter8 = false
	val isCollectionList = false
	val isCollectionSet = false
	val isCollectionMap = true
	val isChallenge = false

	if (isChapter5) {
		//Safe Call Operator
		var nullSafe1 = readLine()?.capitalize()
		println(nullSafe1)

		// Safe Call Operator with let
		var nullSafe2 = readLine()?.let{
			if (it.isNotBlank()) {
				it.capitalize()
			} else {
				"empty space"
			}
		}
		println(nullSafe2)

		// Non-Null Assertion Operation
		var nullSafe3 = readLine()!!.capitalize()
		println(nullSafe3)

		// if..else
		var nullSafe4 = readLine()
		if (nullSafe4 != null) {
			nullSafe4 = nullSafe3.capitalize()
		} else {
			println("[Error] input value is null.")
		}
		println(nullSafe4)

		// Null Coalescing Operator
		var nullSafe5 = readLine() ?: "empty space"
		println(nullSafe5)

		// Null Coalescing Operator with let
		var nullSafe6 = readLine()?.let {
			it.capitalize()
		} ?: println("input is null")
		println(nullSafe6)
	}

	// ------------------------------------------------------------------------

	if (isChapter7) {
//		placeOrder("shandy,Dragon's Breath,5.91")
//		placeOrder("elixir,Shirley's Temple, 4.12")

		println(toDragonSpeak("Dragon's breath: It's got what adventures crave!"))
		println(toDragonSpeak("DRAGON'S BREATH: IT'S GOT WHAT ADVENTURES CRAVE!"))
	}

	// ------------------------------------------------------------------------

	if (isChapter8) {
//		placeOrder("shandy,Dragon's Breath,5.91")
//		placeOrder("shandy,Dragon's Breath,12.1")
	}

	// ------------------------------------------------------------------------

	if (isCollectionList) {

		// 주요 함수
//		println(patronList[0])
//		println(patronList.first())
//		println(patronList.last())
//		println(patronList.getOrElse(5) { "Unknown Patron" })
//		println(patronList.getOrNull(6) ?: "Unknwon Patron")

		if (patronList.contains("Eli")) {
			println("술집 주인이 말한다: Eli는 안쪽 방에서 카드하고 있어요.")
		} else {
			println("술집 주인이 말한다: Eli는 여기 없어요.")
		}

		if (patronList.containsAll(listOf("Sophie", "Mordoc"))) {
			println("술집 주인이 말한다: 네, 모두 있어요.")
		} else {
			println("술집 주인이 말한다: 아니오, 나간 사람도 있습니다.")
		}

		patronList.remove("Eli");
		patronList.add("Alex")
		patronList.add(0, "Alex")
		patronList[0] = "Alexis"
		println(patronList)
		println()

		// 반복
//		for (patron in patronList) {
//			println("좋은 밤입니다, $patron 님")
//		}
		patronList.forEachIndexed { index, patron ->
			println("좋은 밤입니다, $patron 님 - 당신은 #${index + 1} 번째 입니다.")
			placeOrder(patron, menuList.random())
		}

		menuList.forEachIndexed { index, data ->
			println("$index : $data")
		}
	}

	if (isCollectionSet) {
		(0..9).forEach {
			val first = patronList.random()
			val last = lastName.random()
			val name = "$first $last"
//			uniquePatrons += name
			uniquePatrons.add(name)
		}
		println(uniquePatrons)
	}

	if (isChallenge) {
		println("*** Welcome to Taernyl's Folly ***")
		menuList.forEach { menu ->
			val (_, name, price) = menu.split(',')
			print(name.capitalize())
			(0..33 - name.length - price.length).forEach { print(".") }
			println(price)
		}
	}

	if (isCollectionMap) {
		(0..9).forEach {
			val first = patronList.random()
			val last = lastName.random()
			val name = "$first $last"
			uniquePatrons.add(name)
		}

		uniquePatrons.forEach {
			patronGold[it] = 6.0
			placeOrder(it, menuList.random())
		}
		displayPatronBalances()
	}
}

fun performPurchaseV2(price: Double, patronName: String) {
	val totalPurse = patronGold.getValue(patronName)
	patronGold[patronName] = totalPurse - price
}

private fun displayPatronBalances() {
	patronGold.forEach { patron, balance ->
		println("$patron, balanace: ${"%.2f".format(balance)}")
	}
}

@Deprecated("Not Used")
fun performPurchase(price: Double) {
	displayBalance()

	val totalPurse = playerGold + ( playerSilver / 100.0)
	println("지갑 전체 금액: 금화 $totalPurse")

	if (totalPurse - price > 0) {
		println("금화 $price 로 술을 구입함")
	} else {
		println("잔액부족")
		return
	}

	val remainingBalance = totalPurse - price
	println("남은 잔액: ${"%.2f".format(remainingBalance)}")

	val remainingGold = remainingBalance.toInt()
	val remainingSilver = (remainingBalance % 1 * 100).roundToInt()
	playerGold = remainingGold
	playerSilver = remainingSilver
	displayBalance()
}

@Deprecated("Not Used")
private fun displayBalance() {
	println("플레이어의 지갑 잔액: 금화 $playerGold 개, 은화 $playerSilver 개")
}

private fun toDragonSpeak(phrase: String) =
	phrase.replace(Regex("[aeiou]", RegexOption.IGNORE_CASE)) {
		when(it.value) {
			"a", "A" -> "4"
			"e", "E" -> "3"
			"i", "I" -> "1"
			"o", "O" -> "0"
			"u", "U" -> "|_|"
			else -> it.value
		}
	}

private fun placeOrder(patronName: String, menuData: String) {
	val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
	val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
	println("$patronName 은 $tavernMaster 에게 주문한다.")

	val (type, name, price) = menuData.split(',')

	if (price.toDouble() <= patronGold.getValue(patronName)) {
		println("$patronName 은 금화 $price 로 $name ($type)를 구입한다.")
	} else {
		println("$patronName 은 돈이 없어 쭂겨났다.")
		println()
		return
	}
	
//	val message = "$patronName 은 금화 $price 로 $name ($type)를 구입한다."
//	println(message)

//	performPurchase(price.toDouble())
	performPurchaseV2(price.toDouble(), patronName)

	val phrase = if (name == "Dragon's Breath") {
		"$patronName 이 감탄한다: ${toDragonSpeak("와, $name 진짜 좋구나!")}"
	} else {
		"$patronName 이 말한다: 감사합니다 $name."
	}
	println(phrase)
	println()
}