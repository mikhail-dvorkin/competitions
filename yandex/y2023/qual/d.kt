package yandex.y2023.qual

private fun solve(): String {
	val (n, m) = readInts()
	data class Edge(val from: Int, val to: Int, val weight: Int)
	val edges = List(m) {
		val tokens = readStrings()
		val a = tokens[0].toInt() - 1
		val b = tokens[1].toInt()
		val greater = (tokens[2][0] == '>')
		val value = tokens[3].toInt()
		if (greater) Edge(a, b, -value) else Edge(b, a, value)
	}
	val p = LongArray(n + 1)
	for (iter in 0..n) {
		for (edge in edges) {
			if (p[edge.to] > p[edge.from] + edge.weight) {
				if (iter == n) return "NO"
				p[edge.to] = p[edge.from] + edge.weight
			}
		}
	}
	return "YES"
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(1) { println(solve()) }
