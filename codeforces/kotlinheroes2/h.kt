private fun solve() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
		nei[b].add(a)
	}
	var low = 0
	var high = n - 1
	while (low + 1 < high) {
		val mid = (low + high) / 2

	}
}

private fun dfs(nei: List<MutableList<Int>>, p: MutableList<Int>, cool: List<Boolean>, v: Int, parent: Int): Pair<Int, Int> {
	var best = if (cool[v]) 0 to v else -nei.size to -1
	p[v] = parent
	for (u in nei[v]) {
		if (u == parent) continue
		val (dist, vertex) = dfs(nei, p, cool, u, v)
		if (dist + 1 > best.first) best = dist + 1 to vertex
	}
	return best
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
