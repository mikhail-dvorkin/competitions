package codeforces.educationalround95

private fun solve() {
	val (x, y, k) = readInts()
	val a = (k.toLong() * (1 + y) - x - 3) / (x - 1)
	println(a + k)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
