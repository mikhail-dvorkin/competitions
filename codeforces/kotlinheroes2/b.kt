package codeforces.kotlinheroes2

fun main() {
	readLine()
	val a = readInts()
	println(a.mapIndexed { index, v -> if (v == 0) 0L else 1 + index + a.size * (v - 1L) }.max())
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
