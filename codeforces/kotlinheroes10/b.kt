package codeforces.kotlinheroes10

private fun solve(): Int {
	val (k, nIn) = readInts()
	val n = nIn % (3 * k)
	return maxOf(2 * k - n, 0)
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { println(solve()) }
