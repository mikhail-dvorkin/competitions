package atcoder.arc156

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val degree = IntArray(n) { nei[it].size }
	val answer = IntArray(n) { it }
	val leaves = nei.indices.filter { degree[it] == 1 }.toMutableList()
	fun popLeaf(v: Int) {
		for (u in nei[v]) if (--degree[u] == 1) leaves.add(u)
	}
	repeat(n / 2) {
		val (u, v) = List(2) { leaves.removeLast() }
		answer[u] = v
		answer[v] = u
		popLeaf(u)
		popLeaf(v)
	}
	println(answer.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
