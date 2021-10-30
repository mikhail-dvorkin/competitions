package codeforces.deltix2021summer

private fun solve() {
	val (c, d) = readInts()
	if (c == d && c == 0) return println(0)
	if (c == d && c > 0 || c == -d) return println(1)
	if (c + d > 0 && (c + d) % 2 == 0) return println(2)
	println(-1)
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
