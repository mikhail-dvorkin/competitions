package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val (a, b) = readInts()
	println(a + b)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
