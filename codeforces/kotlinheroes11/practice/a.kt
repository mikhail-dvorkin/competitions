package codeforces.kotlinheroes11.practice

private fun solve() {
	println(readInts().sorted().joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
