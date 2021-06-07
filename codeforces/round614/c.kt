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
	for (root in nei.indices) {
		fun dfs(u: Int, p: Int) {
			par[root][u] = p
			for (v in nei[u]) if (v != p) {
				dfs(v, u)
				count[root][u] += count[root][v]
			}
		}
		dfs(root, -1)
	}
	val a = List(n) { LongArray(n) { -1 } }
	fun solve(u: Int, v: Int): Long {
		if (u == v) return 0
		if (a[u][v] == -1L) a[u][v] = maxOf(solve(par[v][u], v), solve(u, par[u][v])) + count[v][u] * count[u][v]
		return a[u][v]
	}
	println(nei.indices.map { u -> nei.indices.maxOf { v -> solve(u, v) } }.maxOrNull())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
