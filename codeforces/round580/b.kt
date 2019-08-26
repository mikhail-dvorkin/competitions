package codeforces.round580

fun main() {
	readLine()
	val a = readLine()!!.split(" ").map { it.toLong() }
	val perBit = List(Long.SIZE_BITS) { i -> a.filter { (it shr i) and 1 != 0L } }
	if (perBit.any { it.size >= 3 }) {
		println(3)
		return
	}
	val vertices = perBit.flatten().toSet()
	val edges = perBit.filter { it.size == 2 }.map { pair -> pair.map { vertices.indexOf(it) } }
	val inf = vertices.size + 1
	val eOriginal = List(vertices.size) { IntArray(vertices.size) { inf } }
	for ((u, v) in edges) {
		eOriginal[u][v] = 1
		eOriginal[v][u] = 1
	}
	val ans = edges.map { (u, v) ->
		val e = eOriginal.map { it.clone() }
		e[u][v] = inf
		e[v][u] = inf
		floyd(e)
		e[u][v] + 1
	}.min() ?: inf
	println(if (ans < inf) ans else -1)
}

private fun floyd(e: List<IntArray>) {
	for (k in e.indices) {
		for (i in e.indices) {
			for (j in e.indices) {
				e[i][j] = minOf(e[i][j], e[i][k] + e[k][j])
			}
		}
	}
}
