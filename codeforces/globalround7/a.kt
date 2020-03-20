package codeforces.globalround7

private fun solve() {
	val n = readInt()
	if (n == 1) return println(-1)
	val ans = List(1) { 8 } + List(n - 1) { 9 }
	println(ans.joinToString(""))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
