package codeforces.round580

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
		nei[b].add(a)
	}
	nei.indices.flatMap { v ->
		val (sizes, vertices) = nei[v].map { Pair(dfs(it, setOf(v), 0, nei), it) }.sortedBy { it.first }.unzip()
		sizes.indices.map { i ->
			val size = sizes.take(i).sum()
			Pair((size + 1) * (n - size)) { dfs(v, vertices.drop(i), 1, nei); dfs(v, vertices.take(i), size + 1, nei) }
		}
	}.maxByOrNull { it.first }?.second?.invoke()
}

private fun dfs(v: Int, p: Collection<Int>, k: Int, nei: List<List<Int>>): Int {
	var sum = 1
	for (u in nei[v].minus(p)) {
		if (k > 0) println("${u + 1} ${v + 1} ${k * sum}")
		sum += dfs(u, setOf(v), k, nei)
	}
	return sum
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
