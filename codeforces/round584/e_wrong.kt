package codeforces.round584

import kotlin.math.abs

private fun solve() {
	val (n, m) = readInts()
	val a = List(n) { readInts() }
	val b = (0 until m).map { i -> a.map { it[i] }.toIntArray() }.sortedByDescending { it.max() }.take(n)
	println(solve(b, 1, b[0], b[0].sum()))
}

var tries = 3300000

private fun solve(b: List<IntArray>, x: Int, best: IntArray, bestInit: Int): Int {
	if (x == b.size) {
		tries--
		return bestInit
	}
	val rotated = b[x]
	val n = rotated.size
	val nb = best.clone()
	var tried = false
	var bestFound = bestInit
	val seen = mutableSetOf<Int>()
	val tt = abs(tries)
	for (i in 0 until n) {
		for (j in 0 until n) {
			nb[j] = maxOf(best[j], rotated[(tt + i + j) % n])
		}
		val nbSum = nb.sum()
		if (nbSum <= bestInit) continue
		val nbHash = nb.contentHashCode()
		if (!seen.add(nbHash)) continue
		bestFound = maxOf(bestFound, solve(b, x + 1, nb, nbSum))
		if (tries < 0) break
		tried = true
	}
	if (tried) return bestFound
	return solve(b, x + 1, best, bestInit)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
