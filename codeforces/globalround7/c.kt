package codeforces.globalround7

private const val M = 998244353

fun main() {
	val (n, k) = readInts()
	val p = readInts()
	val indices = p.indices.filter { p[it] >= n - k + 1 }
	val a = indices.map { p[it].toLong() }.sum()
	val b = indices.zipWithNext().fold(1L) { acc, pair -> acc * (pair.second - pair.first) % M }
	println("$a $b")
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
