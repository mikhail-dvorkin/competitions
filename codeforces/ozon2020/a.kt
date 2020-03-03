package codeforces.ozon2020

private fun solve() {
	readLn()
	repeat(2) { println(readInts().sorted().joinToString(" ")) }
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
