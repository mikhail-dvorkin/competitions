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
	if (nei.any { it.size == 2 }) {
		println("NO")
		return
	}
	println("YES")
	val ans = nei.flatten().filter { it.from < it.to }.flatMap { edge ->
		val a = dfs(edge.from, edge.to, false, nei)
		val b = dfs(edge.from, edge.to, true, nei)
		val c = dfs(edge.to, edge.from, false, nei)
		val d = dfs(edge.to, edge.from, true, nei)
		val halfValue = edge.value / 2
		listOf(
				Edge(a, c, halfValue),
				Edge(b, d, halfValue),
				Edge(a, b, -halfValue),
				Edge(c, d, -halfValue)
		).filter { it.from != it.to }
	}
	println(ans.size)
	for (edge in ans) {
		println("${edge.from + 1} ${edge.to + 1} ${edge.value}")
	}
}

private data class Edge(val from: Int, val to: Int, val value: Int)

private fun dfs(v: Int, p: Int, order: Boolean, nei: List<List<Edge>>): Int {
	if (nei[v].size == 1) return v
	for (edge in (if (order) nei[v] else nei[v].reversed())) {
		val u = edge.to
		if (u == p) continue
		return dfs(u, v, order, nei)
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
