package gcj.y2020.round2

private fun solve(): String {
	val (n, m) = readInts()
	val r = readInts()
	data class Edge(val to: Int, val id: Int)
	val nei = List(n) { mutableListOf<Edge>() }
	val edges = List(m) {
		val (u, v) = readInts().map { u -> u - 1 }
		nei[u].add(Edge(v, it)); nei[v].add(Edge(u, it))
		u to v
	}
	val added = IntArray(n)
	val byTime = mutableListOf<Int>()
	val ansTime = IntArray(n)
	for (v in 1 until n) {
		val x = r[v - 1]
		if (x > 0) {
			byTime.add(v)
			ansTime[v] = x
			continue
		}
		var i = -x
		while (added[i] != 0) i++
		added[i] = v
	}
	byTime.sortBy { v -> -r[v - 1] }
	for (i in 1 until n) {
		if (added[i] != 0) continue
		added[i] = byTime.pop()
	}
	var x = 1
	var time = 1
	while (x < n) {
		var y = x
		while (y < n && r[added[y] - 1] < 0 && r[added[y] - 1] == r[added[x] - 1]) y++
		var z = x
		while (z < n && r[added[z] - 1] > 0 && r[added[z] - 1] == r[added[x] - 1]) z++
		if (y > x) {
			val good = (x until y).all { i ->
				val v = added[i]
				nei[v].any { edge -> ansTime[edge.to] < time }
			}
			if (!good) time++
			for (i in x until y) {
				ansTime[added[i]] = time
			}
			time++
			x = y
		} else {
			time = r[added[x] - 1] + 1
			x = z
		}
	}
	return edges.joinToString(" ") { (u, v) ->
		(ansTime[u] - ansTime[v]).abs().coerceAtLeast(1).toString()
	}
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun <E> MutableList<E>.pop() = removeAt(lastIndex)
private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
