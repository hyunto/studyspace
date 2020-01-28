package sandbox

const val MAX_EXPERIENCE: Int = 5000

fun main(args: Array<String>) {
//	MAX_EXPERIENCE = 10000	// 컴파일 에러 : Val cannot be reassigned

	val playerName = "현토"
// 	playerName = "hyunto"	// 컴파일 에러 : Val cannot be reassigned

	var experiencePoints = 5
//	var experiencePoints: Int = "thirty-two"	// Type mismatch 컴파일 에러
	experiencePoints += 5;

	var hasSteed = false
	var coin = 0

	val barName = "유니콘의 뿔"
	val barOwnerName = "마르티나"
	val barMenu = mapOf<String, Int>("beer" to 3500, "soju" to 3000)

	println(playerName)
	println(coin)
	println(hasSteed)
	println(experiencePoints)
	println()
	println(barName)
	println(barOwnerName)
	barMenu.forEach { (k, v) -> println("[Menu] $k : $v") }
	println()
	println(playerName.reversed())
}