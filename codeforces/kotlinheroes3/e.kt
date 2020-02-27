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
		nei[v].remove(u); nei[u].remove(v)
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

/*
4

10 4
4 5
5 2
2 1
1 3
1 9
9 10
2 7
7 8
5 6

4 3
1 2
2 3
3 4

5 3
1 2
1 3
1 4
1 5

4 1
1 2
2 4
2 3

 */
