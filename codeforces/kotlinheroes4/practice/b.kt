package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val (a1, b1) = readInts()
	val (a2, b2) = readInts()
	val square = maxOf(a1, b1) == maxOf(a2, b2) && minOf(a1, b1) + minOf(a2, b2) == maxOf(a1, b1)
	println(if (square) "Yes" else "No")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
