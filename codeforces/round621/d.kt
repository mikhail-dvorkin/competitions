package codeforces.round621

fun main() {
	val (n, m, _) = readInts()
	val special = readInts().map { it - 1 }.toSet()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val s = bfs(nei, 0)
	val t = bfs(nei, n - 1)
	val init = t[0]
	var ans = - 3 * n
	val order = s.indices.sortedBy { s[it] - t[it] }
	var maxPrevS = - 3 * n
	for (i in order) {
		if (!special.contains(i)) continue
		ans = maxOf(ans, maxPrevS + t[i])
		maxPrevS = maxOf(maxPrevS, s[i])
	}
	var maxPrevT = - 3 * n
	for (i in order.reversed()) {
		if (!special.contains(i)) continue
		ans = maxOf(ans, maxPrevT + s[i])
		maxPrevT = maxOf(maxPrevT, t[i])
	}
	println(minOf(init, ans + 1))
}

private fun bfs(nei: List<MutableList<Int>>, s: Int): MutableList<Int> {
	val n = nei.size
	val inf = n
	val queue = mutableListOf(s)
	val dist = MutableList(nei.size) { inf }
	dist[s] = 0
	var low = 0
	while (low < queue.size) {
		val v = queue[low++]
		for (u in nei[v]) {
			if (dist[u] == inf) {
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
