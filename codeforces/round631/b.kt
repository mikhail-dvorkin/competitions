package codeforces.round631

private fun solve() {
	val (d, m) = readInts()
	val ans = d.toString(2).indices.fold(1) { acc, i ->
		val from = 1 shl i
		val to = minOf(1 shl (i + 1), d + 1)
		(acc * (to - from + 1L) % m).toInt()
	}
	println((ans + m - 1) % m)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
