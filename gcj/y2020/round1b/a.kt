package gcj.y2020.round1b

import kotlin.math.absoluteValue

private fun solve(): String {
	var (x, y) = readInts()
	for (i in 1..60) {
		val solve = solve(x.toLong(), y.toLong(), i)
		if (solve != null) return solve
	}
	return "IMPOSSIBLE"
}

private fun solve(x: Long, y: Long, steps: Int): String? {
	var x = x
	var y = y
	var ans = ""
	for (i in steps - 1 downTo 0) {
		val move = 1L shl i
		if (x.absoluteValue > y.absoluteValue) {
			if (x > 0) {
				x -= move
				ans += "E"
			} else {
				x += move
				ans += "W"
			}
		} else {
			if (y > 0) {
				y -= move
				ans += "N"
			} else {
				y += move
				ans += "S"
			}
		}
	}
	if (x == 0L && y == 0L) return ans.reversed()
	return null
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
