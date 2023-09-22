package codeforces.codeton6

private fun solve() {
	val (n, k, x) = readInts()
	if (k - 1 > x || k > n) return println(-1)
	println(k * (k - 1) / 2 + (n - k) * (if (x != k) x else x - 1))
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
