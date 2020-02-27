package codeforces.kotlinheroes3

private fun solve() {
	val (n, k) = readInts()
	val a = readInts()
	val b = a.sorted()
	val bMax = b.last()
	val forTogether = b.mapIndexed { index, v -> bMax - (n - 1L - index) - v }.sum()
	val moves = if (forTogether >= k) 0 else ((k - forTogether + n - 1) / n)
	var v = bMax + moves
	val d = mutableMapOf<Int, Long>()
	var toSpend = k.toLong()
	for (i in b.indices.reversed()) {
		val spend = minOf(v - b[i], toSpend)
		d[b[i]] = spend
		toSpend -= spend
		v--
	}
	println(a.map { d[it] }.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
