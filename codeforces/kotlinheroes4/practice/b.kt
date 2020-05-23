package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val (a1, b1, a2, b2) = List(2) { readInts().sorted() }.flatten()
	println((b1 == b2 && a1 + a2 == b1).iif("Yes", "No"))
}

private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
