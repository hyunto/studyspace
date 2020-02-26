package sandbox

val String.numVowels
	get() = count { "aeiouy".contains(it) }

fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

fun <T> T.easyPrint(): T{
	println(this)
	return this
}

infix fun String?.printWithDefault(default: String) = print(this ?: default)

fun main(args: Array<String>) {
	"Hello World".easyPrint().addEnthusiasm(3).easyPrint()    // Hello World!!! 출력

	"How many vowels?".numVowels.easyPrint()
	
	val nullableString: String? = null
	nullableString printWithDefault "기본 문자열"
	nullableString.printWithDefault("기본 문자열")
}