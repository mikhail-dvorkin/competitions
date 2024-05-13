package codeforces.kotlinheroes10.practice

import kotlin.math.abs

private fun solve() {
	val (_, k) = readInts()
	val s = readln()
	val count = s.groupBy { it }.mapValues { it.value.size }
	var have = 0
	var canHave = 0
	for (c in 'a'..'z') {
		val small = count.getOrDefault(c, 0)
		val large = count.getOrDefault(c.uppercaseChar(), 0)
		have += minOf(small, large)
		canHave += abs(small - large) / 2
	}
	println(have + minOf(canHave, k))
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { solve() }
