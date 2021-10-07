package codeforces.kotlinheroes8.practice

fun main() {
	readLn()
	val a = readInts().withIndex().sortedByDescending { it.value }
	println(a.withIndex().sumOf { it.value.value * it.index + 1 })
	println(a.map { it.index + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
