package codeforces.round633

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val canTake = IntArray(n) { 1 }
	val cannotTake = IntArray(n)
	val canTakeAnswer = IntArray(n) { 1 }
	val cannotTakeAnswer = IntArray(n)
	fun dfs(v: Int, p: Int) {
		nei[v].remove(p)
		if (nei[v].isEmpty()) return
		for (u in nei[v]) dfs(u, v)
		val bestCanTake = nei[v].map { canTake[it] }.sortedDescending().take(2)
		cannotTake[v] = nei[v].size - 1 + bestCanTake[0]
		canTake[v] = maxOf(cannotTake[v], 1 + nei[v].maxOf { cannotTake[it] })
		cannotTakeAnswer[v] = bestCanTake.sum() + nei[v].size - bestCanTake.size
		canTakeAnswer[v] = maxOf(cannotTakeAnswer[v], canTake[v],
			nei[v].maxOf { maxOf(canTakeAnswer[it], cannotTakeAnswer[it] + 1) })
	}
	val leaf = nei.indices.first { nei[it].size == 1 }
	dfs(leaf, -1)
	println(canTakeAnswer[leaf])
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
