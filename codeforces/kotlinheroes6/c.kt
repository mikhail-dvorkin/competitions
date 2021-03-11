package codeforces.kotlinheroes6

import kotlin.math.abs

private fun solve() {
	val (n, xIn, yIn) = readInts()
	val (x, y) = listOf(xIn, yIn).sorted().map { it - 1 }
	val ans = List(n) { m ->
		maxOf(solve(0, m, x), solve(m, n, y))
	}.minOrNull()
	println(ans)
}

private fun solve(from: Int, to: Int, x: Int): Int {
	if (from == to) return 0
	return minOf(dist(x, from), dist(x, to - 1)) + to - from - 1
}

private fun dist(x: Int, y: Int): Int {
	return abs(x - y)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
