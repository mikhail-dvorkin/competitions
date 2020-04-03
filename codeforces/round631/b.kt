package codeforces.round631

private fun solve() {
	val (d, m) = readInts()
	val n = d.toString(2).length
	var a = 1 % m
	for (i in 0 until n) {
		val from = 1 shl i
		val to = minOf(1 shl (i + 1), d + 1)
		a = (a * (to - from + 1L) % m).toInt()
	}
	println((a + m - 1) % m)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
