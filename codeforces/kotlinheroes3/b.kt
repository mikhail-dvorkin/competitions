package codeforces.kotlinheroes3

private fun solve() {
	val segments = List(readInt()) { readInts() }
	val toTry = segments.flatMap { listOf(it[0] - 1, it[0], it[1], it[1] + 1) }
	println(toTry.firstOrNull { x -> segments.count { it[0] <= x && x <= it[1] } == 1 } ?: -1)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
