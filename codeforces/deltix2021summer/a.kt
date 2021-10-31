package codeforces.deltix2021summer

private fun solve(): Int {
	val (c, d) = readInts()
	return when {
		c == d && c == 0 -> 0
		c == d && c > 0 || c == -d -> 1
		c + d > 0 && (c + d) % 2 == 0 -> 2
		else -> -1
	}
}

fun main() {
	repeat(readInt()) { println(solve()) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
