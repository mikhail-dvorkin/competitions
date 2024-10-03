package codeforces.kotlinheroes11.practice

private fun solve() {
	readln()
	val a = readInts().groupBy { it }.toSortedMap().map { it.value.size }
	var smaller = 0
	var ans = 0L
	for (x in a) {
		ans += x * (x - 1L) * (x - 2) / 6 + x * (x - 1L) / 2 * smaller
		smaller += x
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
