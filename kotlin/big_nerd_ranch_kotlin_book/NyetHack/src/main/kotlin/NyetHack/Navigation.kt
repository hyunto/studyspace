package NyetHack

enum class Direction(private val coordinate: Coordinate) {
	NORTH(Coordinate(0, -1)),
	EAST(Coordinate(1, 0)),
	SOUTH(Coordinate(0, 1)),
	WEST(Coordinate(-1, 0));

	fun updateCoordinate(playerCoordinate: Coordinate) =
		Coordinate(playerCoordinate.x + coordinate.x, playerCoordinate.y + coordinate.y)

}

data class Coordinate(val x: Int, val y: Int) {
	val isInBounds = x >= 0 && y >= 0
}

fun getDirectionName(direction: Direction) =
	when (direction) {
		Direction.NORTH -> "북"
		Direction.EAST -> "동"
		Direction.SOUTH -> "남"
		Direction.WEST -> "서"
	}