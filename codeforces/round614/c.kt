package codeforces.round614

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b); nei[b].add(a)
	}
	val par = List(n) { IntArray(n) { 0 } }
	val count = List(n) { IntArray(n) { 1 } }
	fun dfs(root: Int, u: Int, p: Int = -1) {
		par[root][u] = p
		for (v in nei[u]) {
			if (v == p) continue
			dfs(root, v, u)
			count[root][u] += count[root][v]
		}
	}
	for (u in nei.indices) {
		dfs(u, u)
	}
	val solve = List(n) { LongArray(n) { -1 } }
	fun solve(u: Int, v: Int) {
		if (u == v) solve[u][v] = 0
		if (solve[u][v] != -1L) return
		val uu = par[v][u]; val vv = par[u][v]
		solve(uu, v); solve(u, vv)
		solve[u][v] = maxOf(solve[uu][v], solve[u][vv]) + count[v][u] * count[u][v]
	}
	var best = 0L
	for (u in nei.indices) {
		for (v in nei.indices) {
			solve(u, v)
			best = maxOf(best, solve[u][v])
		}
	}
	println(best)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
