package codeforces.globalround16

private fun solve() {
	val (n, s) = readInts()
	val geqToMedian = n / 2 + 1
	println(s / geqToMedian)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
