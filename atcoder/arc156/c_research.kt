package atcoder.arc156

import kotlin.random.Random

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val r = Random(566)
	val byDegree = nei.indices.sortedBy { nei[it].size }
	val p = IntArray(n)
	for (i in nei.indices) {
		p[byDegree[i]] = byDegree[n - 1 - i]
	}
	while (true) {
		var perfect = true
		for (v in nei.indices) {
			for (u in nei[v]) if (u < v) {
				val vv = p[v]
				val uu = p[u]
				if (uu in nei[vv]) {
					if (v == uu && u == vv) continue
					perfect = false
					var w: Int
					while (true) {
						w = r.nextInt(n)
						if (w != v && w != u) break
					}
					val temp = p[v]; p[v] = p[u]; p[u] = p[w]; p[w] = temp
				}
			}
		}
		if (perfect) break
	}
	println(p.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

