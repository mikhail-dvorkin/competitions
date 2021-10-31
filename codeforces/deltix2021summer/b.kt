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
	val balance = a.sum() - n / 2
	if (n % 2 == 0) {
		if (balance == 0) return minOf(solve(a, 0), solve(a, 1))
	} else {
		if (balance in 0..1) return solve(a, balance)
	}
	return -1
}

fun main() {
	repeat(readInt()) { println(solve()) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
