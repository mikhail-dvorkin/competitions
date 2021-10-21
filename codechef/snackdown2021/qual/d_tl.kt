package codechef.snackdown2021.qual

private fun solve() {
	val (n, m) = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val degree = IntArray(n) { nei[it].size }
	val byDegree = List(n) { mutableSetOf<Int>() }
	for (v in nei.indices) byDegree[degree[v]].add(v)
	val mark = BooleanArray(n)
	var minDegree = 0
	var maxSeenMinDegree = 0
	val ans = IntArray(n)
	for (iter in nei.indices) {
		while (byDegree[minDegree].isEmpty()) minDegree++
		maxSeenMinDegree = maxOf(maxSeenMinDegree, minDegree)
		val v = byDegree[minDegree].first()
		ans[v] = n - iter
		byDegree[minDegree].remove(v)
		mark[v] = true
		for (u in nei[v]) {
			if (mark[u]) continue
			byDegree[degree[u]].remove(u)
			degree[u]--
			byDegree[degree[u]].add(u)
			minDegree = minOf(minDegree, degree[u])
		}
	}
	println(maxSeenMinDegree)
	println(ans.joinToString(" "))
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
