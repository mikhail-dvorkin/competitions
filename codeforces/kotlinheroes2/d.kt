package codeforces.kotlinheroes2

fun main() {
	val (_, a, b, k) = readInts()
	val count = readInts().groupBy { it }.mapValues { it.value.size }.toMutableMap()
	val ans = count.keys.sorted().let { if (a > b) it else it.asReversed() }.sumBy { x ->
		val y = x * k
		count[y] ?: return@sumBy 0
		val take = minOf(count[x]!! / a, count[y]!! / b)
		count[x] = count[x]!! - a * take
		count[y] = count[y]!! - b * take
		take
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
