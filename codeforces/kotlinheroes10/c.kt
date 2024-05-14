package codeforces.kotlinheroes10

fun solve(n: Int, k: Long): Int {
	val size = 1L shl n
	if (k > size / 2) return solve(n, size - k)
	if (k == 0L) return 0
	return 1 + solve(n - 1, k)
}

private fun solve(): Int {
	val (n, k) = readLongs()
	return solve(n.toInt(), k)
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }

fun main() = repeat(readInt()) { println(solve()) }
