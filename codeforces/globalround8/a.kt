package codeforces.globalround8

private fun solve() {
	var (a, b, n) = readInts()
	var iter = 0
	while (maxOf(a, b) <= n) {
		iter++
		val c = maxOf(a, b)
		val d = a + b
		a = c
		b = d
	}
	println(iter)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
