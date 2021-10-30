package codeforces.round752

private fun solve(): Number {
	val (x, y) = readInts()
	return when {
		y < x -> x.toLong() * x + y
		y % x == 0 -> x
		else -> y - y % x / 2
	}
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
