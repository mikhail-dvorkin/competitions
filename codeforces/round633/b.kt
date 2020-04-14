package codeforces.round633

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val mark = BooleanArray(n)
	val dist = IntArray(n)
	fun dfs(v: Int) {
		mark[v] = true
		for (u in nei[v]) if (!mark[u]) {
			dist[u] = dist[v] + 1
			dfs(u)
		}
	}
	dfs(0)
	val leaves = nei.indices.filter { nei[it].size == 1 }
	val differentParities = leaves.map { dist[it] % 2 }.toSet().size
	val ansMin = if (differentParities == 1) 1 else 3
	val ansMax = n - 1 - nei.indices.sumBy { v ->
		val neiLeaves = nei[v].count { nei[it].size == 1 }
		maxOf(neiLeaves - 1, 0)
	}
	println("$ansMin $ansMax")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
