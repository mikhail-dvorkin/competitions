package codeforces.globalround16

private fun solve() {
	readLn()
	val a = readInts()
	val ans = a.indices.sumOf { i -> (0 until i).count { j -> a[j] < a[i] } }
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
