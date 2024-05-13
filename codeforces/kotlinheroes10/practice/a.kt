package codeforces.kotlinheroes10.practice

private fun solve() {
	readln()
	val a = readInts()
	val often = a.sorted()[1]
	println(a.indexOfFirst { it != often } + 1)
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { solve() }
