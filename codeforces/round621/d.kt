package codeforces.round621

fun main() {
	val (n, m, _) = readInts()
	val special = readInts().map { it - 1 }
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val (s, t) = listOf(0, n - 1).map { bfs(nei, it) }
	val best = special.sortedBy { s[it] - t[it] }.fold(0 to -n) { (ans, maxPrev), i ->
		maxOf(ans, maxPrev + t[i]) to maxOf(maxPrev, s[i])
	}.first
	println(minOf(t[0], best + 1))
}

private fun bfs(nei: List<MutableList<Int>>, s: Int): List<Int> {
	val n = nei.size
	val queue = mutableListOf(s)
	val dist = MutableList(nei.size) { n }; dist[s] = 0
	var low = 0
	while (low < queue.size) {
		val v = queue[low++]
		for (u in nei[v]) {
			if (dist[u] == n) {
				dist[u] = dist[v] + 1
				queue.add(u)
			}
		}
	}
	return dist
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
