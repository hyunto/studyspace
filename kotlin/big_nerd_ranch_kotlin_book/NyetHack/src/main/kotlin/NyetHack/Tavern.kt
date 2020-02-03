package NyetHack

fun main(args: Array<String>) {
	var beverage = readLine()?.let {
		if (it.isNotBlank()) {
			it.capitalize()
		} else {
			"맥주"
		}
	}
//	beverage = null
	println(beverage)
}