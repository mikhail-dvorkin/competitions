package codeforces.round572

private fun solve() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Edge>() }
	repeat(n - 1) {
		val (aInput, bInput, value) = readInts()
		val a = aInput - 1
		val b = bInput - 1
		nei[a].add(Edge(a, b, value))
		nei[b].add(Edge(b, a, value))
	}
	if (nei.find { it.size == 2 } != null) {
		println("NO")
		return
	}
	println("YES")
	val ans = mutableListOf<Edge>()
	for (v in nei.indices) {
		for (edge in nei[v]) {
			val u = edge.to
			if (u < v) continue
			val a = dfs(u, v, false, nei)
			val b = dfs(u, v, true, nei)
			val c = dfs(v, u, false, nei)
			val d = dfs(v, u, true, nei)
			val value2 = edge.value / 2
			ans.add(Edge(a, c, value2))
			ans.add(Edge(b, d, value2))
			ans.add(Edge(a, b, -value2))
			ans.add(Edge(c, d, -value2))
		}
	}
	ans.removeAll { it.from == it.to }
	println(ans.size)
	for (edge in ans) {
		println("${edge.from + 1} ${edge.to + 1} ${edge.value}")
	}
}

data class Edge(val from: Int, val to: Int, val value: Int)

private fun dfs(v: Int, p: Int, b: Boolean, nei: List<MutableList<Edge>>): Int {
	if (nei[v].size == 1) return v
	for (edge in (if (b) nei[v] else nei[v].reversed())) {
		val u = edge.to
		if (u == p) continue
		return dfs(u, v, b, nei)
	}
	error("")
}

fun main() {
	val stdStreams = (false to true)
	val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
	if (!isOnlineJudge) {
		if (!stdStreams.first) System.setIn(java.io.File("input.txt").inputStream())
		if (!stdStreams.second) System.setOut(java.io.PrintStream("output.txt"))
	}
	val tests = if (isOnlineJudge) 1 else readInt()
	repeat(tests) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
