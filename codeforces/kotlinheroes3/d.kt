package codeforces.kotlinheroes3

private fun solve() {
	val (n, k) = readInts()
	val a = readInts()
	val b = a.sortedDescending()
	val toConnect = b.indices.map { b[0].toLong() - it - b[it] }.sum()
	val top = b[0] + maxOf(k - toConnect + n - 1, 0).toInt() / n
	val increase = mutableMapOf<Int, Int>()
	b.indices.fold(k) { toSpend, i ->
		val spend = minOf(top - i - b[i], toSpend)
		increase[b[i]] = spend
		toSpend - spend
	}
	println(a.map { increase[it] }.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
