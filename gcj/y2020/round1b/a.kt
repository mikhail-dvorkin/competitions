package gcj.y2020.round1b

private fun solve(): String {
	var (x, y) = readInts()
	val steps = (x.abs() + y.abs()).countSignificantBits()
	return (steps - 1 downTo 0).map { i ->
		val dir = DX.indices.maxByOrNull { x * DX[it] + y * DY[it] }!!
		x -= DX[dir] shl i; y -= DY[dir] shl i
		DIR_ROSE[dir]
	}.joinToString("").reversed().takeIf { x == 0 && y == 0 } ?: "IMPOSSIBLE"
}

private val DX = intArrayOf(1, 0, -1, 0)
private val DY = intArrayOf(0, 1, 0, -1)
private const val DIR_ROSE = "ENWS"

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Int.countSignificantBits() = Int.SIZE_BITS - Integer.numberOfLeadingZeros(this)
private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
