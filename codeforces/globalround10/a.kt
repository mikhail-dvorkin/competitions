package codeforces.globalround10

private fun solve() {
	readLn()
	val a = readInts()
	println(if (a.min() == a.max()) a.size else 1)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
