package codeforces.round584

private fun solve() {
	val (n, m) = readInts()
	val a = List(n) { readInts() }
	val b = List(m) { i -> a.map { it[i] } }.sortedByDescending { it.maxOrNull() }.take(n)
	println(solve(b, 1, b[0]))
}

private fun solve(b: List<List<Int>>, x: Int, best: List<Int>): Int {
	val bestInit = best.sum()
	if (x == b.size) return bestInit
	return best.indices.mapNotNull { i ->
		val newBest = List(best.size) { maxOf(best[it], b[x][(it + i) % best.size]) }
		if (newBest.sum() > bestInit) solve(b, x + 1, newBest) else null
	}.maxOrNull() ?: solve(b, x + 1, best)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
