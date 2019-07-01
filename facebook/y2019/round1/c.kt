package facebook.y2019.round1

private fun solve(): Int {
	val (n, hei) = readInts()
	val ladders = List(n) {	val (x, yFrom, yTo) = readInts(); Ladder(it, x, yFrom, yTo) }

	val e = Array(n + 2) { IntArray(n + 2) }
	val ys = ladders.flatMap { listOf(it.yFrom, it.yTo) }.toSet().sorted()
	for ((y, yNext) in ys.zipWithNext()) {
		val idsOrdered = ladders.filter { (it.yFrom <= y) and (y < it.yTo) }.sortedBy { it.x }.map { it.id }
		for ((i, j) in idsOrdered.zipWithNext()) {
			e[i][j] += yNext - y
			e[j][i] += yNext - y
		}
	}
	val inf = n * hei
	for ((i, ladder) in ladders.withIndex()) {
		if (ladder.yFrom == 0) { e[n][i] = inf }
		if (ladder.yTo == hei) { e[i][n + 1] = inf }
	}
	return edmonsKarp(e, n, n + 1).takeIf { it < inf } ?: -1
}

fun edmonsKarp(c: Array<IntArray>, s: Int, t: Int): Int {
	val n = c.size
	val f = Array(n) { IntArray(n) }
	val queue = IntArray(n)
	val prev = IntArray(n)
	var res = 0
	while (true) {
		queue[0] = s
		var low = 0
		var high = 1
		prev.fill(-1)
		prev[s] = s
		while (low < high && prev[t] == -1) {
			val v = queue[low]
			low++
			for (u in 0 until n) {
				if (prev[u] != -1 || f[v][u] == c[v][u]) {
					continue
				}
				prev[u] = v
				queue[high] = u
				high++
			}
		}
		if (prev[t] == -1) {
			break
		}
		var flow = Int.MAX_VALUE
		var u = t
		while (u != s) {
			flow = minOf(flow, c[prev[u]][u] - f[prev[u]][u])
			u = prev[u]
		}
		u = t
		while (u != s) {
			f[prev[u]][u] += flow
			f[u][prev[u]] -= flow
			u = prev[u]
		}
		res += flow
	}
	return res
}

private data class Ladder(val id: Int, val x: Int, val yFrom: Int, val yTo: Int)

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private val isOnlineJudge = System.getenv("ONLINE_JUDGE") == "true"
@Suppress("unused")
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
