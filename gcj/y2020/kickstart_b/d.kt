package gcj.y2020.kickstart_b

import kotlin.math.pow

private fun solve(): Double {
	val (w, h, x1, y1, x2, y2) = readInts()
	return solve(x1 - 1, y2 - 1, h) + solve(y1 - 1, x2 - 1, w)
}

private fun solve(x: Int, y: Int, h: Int): Double {
	if (y + 1 >= h) return 0.0
	var res = 0.0
	var c = 1.0
	var p = x + y
	for (i in 0 until x) {
		res += c * (2.0).pow(-p)
		c *= x + y - i
		c /= i + 1
		while (c < 1) { p++; c *= 2 }
		while (c > 1) { p--; c /= 2 }
	}
	return res
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

operator fun <T> List<T>.component6(): T = get(5)
