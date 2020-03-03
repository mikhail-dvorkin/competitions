package codeforces.ozon2020

private fun solve() {
	readLn()
	val a = readInts()
	val b = readInts()
	println(a.sorted().joinToString(" "))
	println(b.sorted().joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
