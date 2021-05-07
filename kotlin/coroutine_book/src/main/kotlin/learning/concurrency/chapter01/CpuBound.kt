package learning.concurrency.chapter01

fun isPalindrome(word: String): Boolean {
	val lcWord = word.toLowerCase()
	return lcWord == lcWord.reversed()
}

fun filterPalindromes(words: List<String>): List<String> {
	return words.filter { isPalindrome(it) }
}

fun main(args: Array<String>) {
	val words = listOf("level", "pope", "needle", "Anna", "Pete", "noon", "stats")
	filterPalindromes(words).forEach { println(it) }
}