package codeforces.globalround16

private fun solve() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (v, u) = readInts().map { it - 1 }
		nei[v].add(u)
		nei[u].add(v)
	}
	val kids = IntArray(n)
	val isLeaf = BooleanArray(n)
	val isBud = BooleanArray(n)
	var ans = 0
	fun dfs(v: Int, p: Int = -1) {
		var nonLeaves = false
		for (u in nei[v]) {
			if (u == p) continue
			dfs(u, v)
			if (isBud[u]) continue
			kids[v]++
			if (!isLeaf[u]) nonLeaves = true
		}
		isLeaf[v] = (p != -1 && kids[v] == 0)
		isBud[v] = (p != -1 && !nonLeaves && !isLeaf[v])
		if (isBud[v]) ans--
		if (isLeaf[v]) ans++
		if (v == 0 && kids[v] == 0) ans++
	}
	dfs(0)
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
