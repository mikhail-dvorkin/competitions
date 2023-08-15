package facebook.y2020.round3

import java.lang.StringBuilder

private fun solve(): String {
	var k = readInt() / 2
	val main = minOf(100, k + 2)
	var vertices = main
	val edges = mutableListOf<Pair<Int, Int>>()
	for (i in 1 until main) {
		edges.add(i - 1 to i)
	}
	k -= main - 2
	for (i in 1..main - 2) {
		var deg = 2
		while (true) {
			val next = k + deg * (deg - 1) / 2 - deg * (deg + 1) / 2
			if (next < 0) break
			edges.add(i to vertices)
			vertices++
			deg++
			k = next
		}
	}
	var last = 0
	while (k > 0) {
		edges.add(last to vertices)
		vertices++
		last = vertices
		k--
	}
	if (k != 0) error("" + k)
	val sb = StringBuilder("$vertices ${edges.size}")
	for ((a, b) in edges) {
		sb.append("\n${a + 1} ${b + 1}")
	}
	return sb.toString()
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
