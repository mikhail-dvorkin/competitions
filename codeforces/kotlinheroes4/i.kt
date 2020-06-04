package codeforces.kotlinheroes4

import java.util.*

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
	val sum = List(1 shl n) { mask -> a.indices.filter { mask shr it and 1 == 1 }.map { a[it].toLong() }.sum() }
	val sumAll = sum.last()
	val len = List(n) { List(n) { IntArray(1 shl n) { inf } } }
	for (mask in 0 until (1 shl n)) {
		val ii = a.indices.filter { mask shr it and 1 == 1 }
		if (ii.size == 1) {
			len[ii[0]][ii[0]][mask] = 0
			continue
		}
		for (i in ii) for (j in ii) if (i != j) {
			len[i][j][mask] = ii.map { k -> len[i][k][mask xor (1 shl j)] + e[k][j] }.min()!!
		}
	}
	for (i in e.indices) {
		len[i][i][(1 shl n) - 1] = e.indices.map { k -> len[i][k].last() + e[k][i] }.min()!!
	}
	val sm = List(n) { TreeMap<Long, Int>().also { it[0L] = 0; it[Long.MAX_VALUE] = inf } }
	for (i in e.indices) {
		val smi = sm[i]
		for (mask in 0 until (1 shl n)) {
			if (mask shr i and 1 == 0) continue
			for (j in e.indices) {
				if (mask shr j and 1 == 0) continue
				val eat = sum[mask]
				val edges = len[i][j][mask]
				val nextEntry = smi.ceilingEntry(eat)!!
//				val nextEat = nextEntry.key!!
				val nextEdges = nextEntry.value!!
				if (nextEdges <= edges) continue
				smi[eat] = edges
				while (true) {
					val prevEntry = smi.floorEntry(eat - 1) ?: break
					if (prevEntry.value!! < edges) break
					smi.remove(prevEntry.key!!)
				}
			}
		}
	}
	val init = IntArray(n) { inf }.also { it[s] = 0 }
	val b = mutableListOf(init)
	val seen = mutableMapOf<List<Int>, Int>()
	val seenMin = mutableMapOf<List<Int>, Int>()
	val period: Int
	val periodEdges: Int
	var iter = 0
	while (true) {
		val prev = b.last()
		val next = IntArray(n) { i ->
			e.indices.map { j -> prev[j] + len[j][i].last() }.min()!!
		}
		b.add(next)
		val nextMin = next.min()!!
		val snapshot = next.map { it - nextMin }
		if (snapshot in seen) {
			period = iter - seen[snapshot]!!
			periodEdges = nextMin - seenMin[snapshot]!!
			break
		}
		seen[snapshot] = iter
		seenMin[snapshot] = nextMin
		iter++
	}
	readLongs().forEach { c ->
		val ii = (c / sumAll)
		val toTake = c - sumAll * ii
		var add = 0L
		val bi = if (ii < b.size) b[ii.toInt()] else {
			val takePeriods = (ii - b.size + period) / period
			add = takePeriods * periodEdges
			b[(ii - takePeriods * period).toInt()]
		}
		var best = inf
		for (j in e.indices) {
			val cur = bi[j] + sm[j].ceilingEntry(toTake)!!.value!!
			best = minOf(best, cur)
		}
		println(best + add)
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
