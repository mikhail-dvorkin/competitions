package codeforces.kotlinheroes3

private fun solve() {
	val (n, k) = readInts()
	val nei = List(n) { mutableSetOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val leaves = nei.indices.filter { nei[it].size == 1 }.toMutableList()
	if (k == 1) return println("Yes\n1\n1")
	if (k > leaves.size) return println("No")
	val ans = nei.indices.toMutableSet()
	while (leaves.size > k) {
		val v = leaves.last()
		val u = nei[v].first()
		ans.remove(v)
		nei[u].remove(v)
		leaves.removeAt(leaves.lastIndex)
		if (nei[u].size == 1) leaves.add(u)
	}
	println("Yes\n${ans.size}\n${ans.map { it + 1 }.joinToString(" ")}")
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
