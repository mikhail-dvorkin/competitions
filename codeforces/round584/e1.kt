package codeforces.round584

private fun solve() {
	val (n, m) = readInts()
	val a = List(n) { readInts() }
	val b = (0 until m).map { i -> a.map { it[i] } }.sortedByDescending { it.max() }.take(n)
	println(solve(b, 1, b[0]))
}

private fun solve(b: List<List<Int>>, x: Int, best: List<Int>): Int {
	val bestInit = best.sum()
	if (x == b.size) return bestInit
	val rotated = b[x]
	val n = rotated.size
	val nb = best.toMutableList()
	var tried = false
	var bestFound = bestInit
	for (i in 0 until n) {
		for (j in 0 until n) {
			nb[j] = maxOf(best[j], rotated[(i + j) % n])
		}
		if (nb.sum() <= bestInit) continue
		bestFound = maxOf(bestFound, solve(b, x + 1, nb))
		tried = true
	}
	if (tried) return bestFound
	return solve(b, x + 1, best)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
