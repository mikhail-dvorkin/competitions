package codeforces.round680

fun main() {
	val (n, m, k) = readInts()
	val c = readInts().map { it - 1 }
	val neiSameColor = List(n) { mutableListOf<Int>() }
	val edges = List(m) { readInts().map { it - 1 } }
	for ((a, b) in edges) {
		if (c[a] == c[b]) {
			neiSameColor[a].add(b)
			neiSameColor[b].add(a)
		}
	}
	val comp1 = IntArray(n) { -1 }
	val color1 = IntArray(n) { 0 }
	val dead = BooleanArray(k)
	var comp1count = 0
	for (vv in neiSameColor.indices) {
		if (comp1[vv] >= 0) continue
		val queue = mutableListOf(vv)
		var low = 0
		while (low < queue.size) {
			val v = queue[low++]
			comp1[v] = comp1count
			for (u in neiSameColor[v]) {
				if (comp1[u] >= 0) {
					if (color1[u] == color1[v]) dead[c[v]] = true
					continue
				}
				color1[u] = 1 - color1[v]
				queue.add(u)
			}
		}
		comp1count++
	}
	val subGraphs = mutableMapOf<Long, MutableList<Long>>()
	for ((u, v) in edges) {
		val c1 = c[u]
		val c2 = c[v]
		if (dead[c1] || dead[c2] || c1 == c2) continue
		val id = minOf(c1, c2) * k.toLong() + maxOf(c1, c2)
		val edge = (color1[v] xor color1[u]).toLong().shl(48) or comp1[v].toLong().shl(24) or comp1[u].toLong()
		if (id !in subGraphs) subGraphs[id] = mutableListOf()
		subGraphs[id]!!.add(edge)
	}
	val goodColors = dead.count { !it }
	println(goodColors.toLong() * (goodColors - 1) / 2 - badPairs(subGraphs))
}

private fun badPairs(subGraphs: MutableMap<Long, MutableList<Long>>): Int {
	var ans = 0
	for (graphEntry in subGraphs) {
		val nei = mutableMapOf<Int, MutableList<Int>>()
		for (edge in graphEntry.value) {
			val xor = edge.shr(48).toInt()
			val c1 = edge.and((1 shl 24) - 1).toInt()
			val c2 = edge.shr(24).and((1 shl 24) - 1).toInt()
			if (c1 !in nei) nei[c1] = mutableListOf()
			nei[c1]!!.add(c2 * 2 + xor)
			if (c2 !in nei) nei[c2] = mutableListOf()
			nei[c2]!!.add(c1 * 2 + xor)
		}
		val mark = mutableMapOf<Int, Int>()
		fun bad(): Int {
			for (entry in nei) {
				if (entry.key in mark) continue
				mark[entry.key] = 0
				val queue = mutableListOf(entry.key)
				var low = 0
				while (low < queue.size) {
					val v = queue[low++]
					val markV = mark[v]!!
					for (edge in nei[v]!!) {
						val xor = edge and 1
						val u = edge shr 1
						val thatColor = markV xor xor
						if ((u in mark)) {
							if (mark[u] != thatColor) return 1
							continue
						}
						mark[u] = thatColor
						queue.add(u)
					}
				}
			}
			return 0
		}
		ans += bad()
	}
	return ans
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
