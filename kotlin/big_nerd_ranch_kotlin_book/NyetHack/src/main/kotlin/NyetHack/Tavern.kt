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
		placeOrder("shandy,Dragon's Breath,5.91")
	}
}

private fun placeOrder(menuData: String) {
	val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
	val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
	println("마드리갈은 $tavernMaster 에게 주문한다.")

//	val data = menuData.split(',')
//	val type = data[0]
//	val name = data[1]
//	val price = data[2]
	val (type, name, price) = menuData.split(',')
	val message = "마드리갈은 금화 $price 로 $name ($type)를 구입한다."
	println(message)
}