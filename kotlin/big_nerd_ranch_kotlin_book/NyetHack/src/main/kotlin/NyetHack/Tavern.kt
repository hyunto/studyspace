package NyetHack

const val TAVERN_NAME = "Taernyl's Folly"

fun main(args: Array<String>) {
	val isChapter5 = false
	val isChapter7 = true

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
		placeOrder("elixir,Shirley's Temple, 4.12")
	}
}

private fun toDragonSpeak(phrase: String) =
	phrase.replace(Regex("[aeiou]")) {
		when(it.value) {
			"a" -> "4"
			"e" -> "3"
			"i" -> "1"
			"o" -> "0"
			"u" -> "|_|"
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

	val phrase = if (name == "Dragon's Breath") {
		"마드리갈이 감탄한다: ${toDragonSpeak("와, $name 진짜 좋구나!")}"
	} else {
		"마드리갈이 말한다: 감사합니다 $name."
	}
	println(phrase)
}