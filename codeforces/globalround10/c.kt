package codeforces.globalround10

private fun solve() {
	readLn()
	println(readInts().zipWithNext { a, b -> maxOf(a.toLong() - b, 0L) }.sum())
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
