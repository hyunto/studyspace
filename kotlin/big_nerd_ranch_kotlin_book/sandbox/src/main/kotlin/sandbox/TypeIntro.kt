package sandbox

fun main(args: Array<String>) {
	val playerName = "현토"
// 	playerName = "hyunto"	// 컴파일 에러 : Val cannot be reassigned

	var experiencePoints = 5
//	var experiencePoints: Int = "thirty-two"	// Type mismatch 컴파일 에러
	experiencePoints += 5;

	println(experiencePoints)
	println(playerName)
}