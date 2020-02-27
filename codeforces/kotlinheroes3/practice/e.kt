package codeforces.kotlinheroes3.practice

fun main() {
	val (_, k) = readInts()
	val r = readInts()
	val less = r.sorted().withIndex().groupBy { it.value }.mapValues { it.value.first().index }
	val mentoring = r.map { less.getValue(it) }.toMutableList()
	repeat(k) {
		val (u, v) = readInts().map { it - 1 }
		if (r[u] > r[v]) mentoring[u]--
		if (r[v] > r[u]) mentoring[v]--
	}
	println(mentoring.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
