package codeforces.globalround20

private fun solve() {
	readLn()
	val a = readInts()
	val moves = a.sumOf { it - 1 }
	println(if (moves % 2 == 0) "maomao90" else "errorgorn")
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
