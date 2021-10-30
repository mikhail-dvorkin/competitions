package codeforces.deltix2021summer
import kotlin.math.abs

private fun solve(a: List<Int>, first: Int): Long {
	val aIndices = a.indices.filter { a[it] == 1 }
	val neededIndices = a.indices.filter { it % 2 == 1 - first }
	return aIndices.zip(neededIndices) { x, y -> abs(x - y).toLong() }.sum()
}

private fun solve(): Long {
	readLn()
	val a = readInts().map { it and 1 }
	val n = a.size
	val ones = a.count { it == 1}
	if (n % 2 == 0) {
		if (ones != n / 2) return -1
		return minOf(solve(a, 0), solve(a, 1))
	} else {
		if (ones == n / 2) return solve(a, 0)
		if (ones == n / 2 + 1) return solve(a, 1)
		return -1
	}
}

fun main() {
	repeat(readInt()) { println(solve()) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
