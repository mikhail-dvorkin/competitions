package codeforces.round606

fun main() = repeat(readInt()) {
	val data = readInts()
	val (n, m) = data.take(2)
	val ab = data.drop(2).map { it - 1 }
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val mark = IntArray(n) { if (it in ab) it else -1 }
	val compTouches = IntArray(n) { 0 }
	fun dfs(v: Int, comp: Int) {
		if (mark[v] != -1) return
		mark[v] = comp
		for (u in nei[v]) {
			dfs(u, comp)
			if (u in ab) compTouches[comp] = compTouches[comp] or (1 shl ab.indexOf(u))
		}
	}
	for (i in 0 until n) dfs(i, i)
	println(List(2) { i -> mark.count { compTouches[it] == 1 shl i }.toLong() }.reduce(Long::times))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
