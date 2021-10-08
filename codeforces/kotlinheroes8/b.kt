package codeforces.kotlinheroes8

private fun solve(): Int {
	readInt()
	val (a, va) = readInts()
	val (_, vc) = readInts()
	val b = readInt()
	return minOf(va + b - a, vc)
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
