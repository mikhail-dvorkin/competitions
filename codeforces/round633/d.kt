package codeforces.round633

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val take = IntArray(n)
	val notTake = IntArray(n)
	val notTakeBothSides = IntArray(n)
	val answer = IntArray(n)
	fun dfs(v: Int, p: Int) {
		val takes = mutableListOf<Int>()
		val notTakes = mutableListOf<Int>()
		val notTakesBothSides = mutableListOf<Int>()
		val better = mutableListOf<Int>()
		for (u in nei[v]) {
			if (u == p) continue
			dfs(u, v)
			takes.add(take[u])
			notTakes.add(notTake[u])
			notTakesBothSides.add(notTakeBothSides[u])
			better.add(maxOf(take[u], notTake[u]))
		}
		if (takes.isEmpty()) {
			take[v] = 1
			notTake[v] = 0
			return
		}
		take[v] = 1 + notTakes.max()!!
		notTake[v] = maxOf(takes.max()!!, notTakes.max()!!) + takes.size - 1
		notTakeBothSides[v] = better.sortedDescending().mapIndexed { index: Int, x: Int -> if (index < 2) x else 1 }.sum()
		answer[v] = listOf(take[v], notTake[v],
				1 + notTakes.sortedDescending().take(2).sum(),
				notTakeBothSides[v],
				1 + notTakesBothSides.max()!!
		).max()!!

	}
	val leaf = nei.indices.first { nei[it].size == 1 }
	dfs(leaf, -1)
	println(answer.max())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
