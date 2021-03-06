package codeforces.round584

private const val M = 1_000_000_007

fun main() {
	val (n, m) = readInts()
	val nei = List(n) { mutableListOf<Pair<String, Int>>() }
	repeat(m) { i ->
		val label = (i + 1).toString()
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(label to b)
		nei[b].add(label to a)
	}
	val gap = m.toString().length
	val inf = gap * n
	val dist = MutableList(n) { inf }
	val text = MutableList(n) { "" }
	val rem = MutableList(n) { 0 }
	val byDist = List(inf) { mutableSetOf<Int>() }
	dist[0] = 0
	byDist[0].add(0)
	for (curDist in byDist.indices) {
		for (v in byDist[curDist]) {
			if (dist[v] < curDist) continue
			for ((label, u) in nei[v]) {
				val newDist = curDist + label.length
				val newText = text[v] + label
				if (newDist >= dist[u] && !(newDist == dist[u] && newText < text[u])) continue
				dist[u] = newDist
				text[u] = newText
				rem[u] = ((rem[v].toString() + label).toLong() % M).toInt()
				byDist[newDist].add(u)
			}
		}
		if (curDist % gap != 0) continue
		val prefixes = mutableListOf<String>()
		for (mode in 0..1) {
			val map = prefixes.sorted().withIndex().associate { it.value to it.index.toString().padStart(gap, '0') }
			for (c in curDist + 1..minOf(curDist + gap, inf - 1)) {
				for (v in byDist[c]) {
					if (dist[v] < c) continue
					val take = text[v].length - (c - curDist)
					val prefix = text[v].take(take)
					prefixes.add(prefix)
					if (mode == 1) text[v] = map[prefix] + text[v].drop(take)
				}
			}
		}
	}
	rem.drop(1).forEach { println(it) }
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
