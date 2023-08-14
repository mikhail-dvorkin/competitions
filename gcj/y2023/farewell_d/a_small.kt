package gcj.y2023.farewell_d

private fun solve(): String {
	val (nLeft, nRight, c) = readInts()
	val (leftIn, rightIn) = List(2) { readInts().map { it - 1 } }
	val n = nLeft + nRight
	return List(c) {
		val (cu, cvIn) = readInts().map { it - 1 }
		val cv = nLeft + cvIn
		val nei = List(n) { mutableListOf<Int>() }
		fun addEdge(u: Int, v: Int) { nei[u].add(v); nei[v].add(u) }
		addEdge(cu, cv)
		for (i in leftIn.indices) addEdge(i, leftIn[i])
		for (i in rightIn.indices) addEdge(nLeft + i, nLeft + rightIn[i])
//		val all = LongArray(n)
//		val down = LongArray(n)
		var ans = 0L
		val size = IntArray(n) { 1 }
		fun dfs(v: Int, p: Int) {
			for (u in nei[v]) if (u != p) {
				dfs(u, v)
				size[v] += size[u]
				ans += size[u].toLong() * (n - size[u])
//				down[v] += down[u] + size[u]
			}
		}
		dfs(0, -1)
		ans * 2.0 / (n * (n - 1L))
	}.joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
