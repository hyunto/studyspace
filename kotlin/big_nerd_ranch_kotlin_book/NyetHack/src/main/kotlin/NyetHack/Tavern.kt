package NyetHack

import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10

fun main(args: Array<String>) {
	val isChapter5 = false
	val isChapter7 = false
	val isChapter8 = true

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
		placeOrder("shandy,Dragon's Breath,12.1")
	}
}

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

private fun placeOrder(menuData: String) {
	val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
	val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
	println("마드리갈은 $tavernMaster 에게 주문한다.")

	val (type, name, price) = menuData.split(',')
	val message = "마드리갈은 금화 $price 로 $name ($type)를 구입한다."
	println(message)

	performPurchase(price.toDouble())

	val phrase = if (name == "Dragon's Breath") {
		"마드리갈이 감탄한다: ${toDragonSpeak("와, $name 진짜 좋구나!")}"
	} else {
		"마드리갈이 말한다: 감사합니다 $name."
	}
	println(phrase)
}