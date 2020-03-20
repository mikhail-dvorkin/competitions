package codeforces.globalround7

fun main() {
	val M = 998244353
	val (n, k) = readInts()
	val p = readInts()
	val a = k * 1L * (n + n - k + 1) / 2
	val indices = p.indices.filter { p[it] >= n - k + 1 }
	val b = indices.zipWithNext().fold(1L) { acc, pair ->
		acc * (pair.second - pair.first) % M
	}
	println("$a $b")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
