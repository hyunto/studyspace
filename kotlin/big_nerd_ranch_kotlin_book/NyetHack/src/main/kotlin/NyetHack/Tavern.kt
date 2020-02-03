package NyetHack

fun main(args: Array<String>) {
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