package codeforces.kotlinheroes4

fun main() {
	val (n, m, _, sIn) = readInts()
	val s = sIn - 1
	val a = readInts()
	val inf = n * n * n
	val e = List(n) { IntArray(n) { inf } }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		e[u][v] = 1
	}

	for (k in e.indices) for (i in e.indices) for (j in e.indices) e[i][j] = minOf(e[i][j], e[i][k] + e[k][j])
	val sum = List(1 shl n) { mask -> mask.oneIndices().map { a[it].toLong() }.sum() }
	val sumAll = sum.last()
	val len = List(n) { List(n) { IntArray(1 shl n) { inf } } }
	for (mask in 0 until (1 shl n)) {
		val indices = mask.oneIndices()
		if (indices.size == 1) len[indices[0]][indices[0]][mask] = 0
		for (i in indices) for (j in indices) if (i != j) {
			len[i][j][mask] = indices.map { k -> len[i][k][mask xor (1 shl j)] + e[k][j] }.min()!!
		}
	}

	val eat = List(n) { i ->
		val map = java.util.TreeMap(mapOf(0L to 0, Long.MAX_VALUE to inf))
		for (mask in sum.indices) if (mask.hasBit(i)) for (j in e.indices) if (mask.hasBit(j)) {
			val eat = sum[mask]
			val edges = len[i][j][mask]
			if (map.ceilingEntry(eat)!!.value!! <= edges) continue
			map[eat] = edges
			while (true) {
				val prevEntry = map.floorEntry(eat - 1) ?: break
				if (prevEntry.value!! < edges) break
				map.remove(prevEntry.key!!)
			}
		}
		map
	}

	val best = mutableListOf(IntArray(n) { inf }.also { it[s] = 0 })
	val seen = mutableMapOf<List<Int>, Pair<Int, Int>>()
	val period: Int; val periodEdges: Int
	while (true) {
		val prev = best.last()
		val next = IntArray(n) { i -> e.indices.map { j -> prev[j] + len[j][i].last() }.min()!! }
		best.add(next)
		val nextMin = next.min()!!
		val snapshot = next.map { it - nextMin }
		if (snapshot in seen) {
			period = seen.size - seen[snapshot]!!.first
			periodEdges = nextMin - seen[snapshot]!!.second
			break
		}
		seen[snapshot] = seen.size to nextMin
	}

	println(readLongs().map { c ->
		val i = c / sumAll
		val toTake = c - sumAll * i
		val takePeriods = maxOf(i - best.size + period, 0) / period
		val bestI = best[(i - takePeriods * period).toInt()]
		takePeriods * periodEdges +	e.indices.map { j -> bestI[j] + eat[j].ceilingEntry(toTake)!!.value!! }.min()!!
	}.joinToString("\n"))
}

private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
private fun Int.oneIndices() = (0 until countSignificantBits()).filter { (this shr it) and 1 != 0 }
private fun Int.countSignificantBits() = Int.SIZE_BITS - Integer.numberOfLeadingZeros(this)

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
