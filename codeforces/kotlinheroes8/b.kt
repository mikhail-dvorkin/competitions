package codeforces.kotlinheroes8

private fun solve(): Int {
	val n = readInt()
	val (a, va) = readInts()
	val (c, vc) = readInts()
	val b = readInt()
	val d = b - a
	return minOf(va + d, vc)
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
