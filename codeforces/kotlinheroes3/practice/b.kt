package codeforces.kotlinheroes3.practice

fun main() {
	readLn()
	val a = readInts()
	val ans = a.toSet().sortedBy { a.lastIndexOf(it) }
	println("${ans.size}\n${ans.joinToString(" ")}")
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
