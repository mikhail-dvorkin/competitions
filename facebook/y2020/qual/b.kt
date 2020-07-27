package facebook.y2020.qual

import kotlin.math.abs

private fun solve(): String {
	readLn()
	val s = readLn()
	return if (abs(s.count { it == 'A' } * 2 - s.length) == 1) "Y" else "N"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
