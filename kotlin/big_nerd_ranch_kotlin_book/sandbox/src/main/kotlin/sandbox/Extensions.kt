package sandbox

val String.numVowels
	get() = count { "aeiouy".contains(it) }

fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

fun <T> T.easyPrint(): T{
	println(this)
	return this
}

fun main(args: Array<String>) {
	"Hello World".easyPrint().addEnthusiasm(3).easyPrint()    // Hello World!!! 출력

	"How many vowels?".numVowels.easyPrint()
}