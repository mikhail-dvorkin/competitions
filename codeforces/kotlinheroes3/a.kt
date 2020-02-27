package codeforces.kotlinheroes3

import kotlin.math.abs

fun main() = repeat(readInt()) { solve() }

private fun solve() {
	val poss = mutableListOf<Pair<Int, String>>()
	for (i in 0..999) poss.add(i to "$i")
	for (i in 1..999) poss.add(i * 1000 to "$i" + "K")
	for (i in 1..2000) poss.add(i * 1000_000 to "$i" + "M")
	val n = readInt()
	var bestDist = Int.MAX_VALUE
	var bestStr = ""
	for ((x, s) in poss) {
		val dist = abs(x - n)
		if (dist <= bestDist) {
			bestDist = dist
			bestStr = s
		}
	}
	println(bestStr)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
