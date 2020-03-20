package codeforces.globalround7

private fun solve() {
	val n = readInt()
	if (n == 1) return println(-1)
	println((listOf(8) + List(n - 1) { 9 }).joinToString(""))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
