package codeforces.kotlinheroes3.practice

fun main() {
	val a = readInts()
	println(a.map { a.max()!! - it }.filter { it > 0 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
