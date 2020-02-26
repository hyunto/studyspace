package sandbox

fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

fun Any.easyPrint() = println(this)

fun main(args: Array<String>) {
	"Hello World".addEnthusiasm(3).easyPrint()    // Hello World!!! 출력
	19.easyPrint()	// 19 출력
}