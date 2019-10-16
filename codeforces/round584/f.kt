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
	val gap = 6
	val inf = gap * n
	val dist = MutableList(n) { inf }
	val text = MutableList(n) { "" }
	val rem = MutableList(n) { 0 }
	val byDist = List(inf) { mutableSetOf<Int>() }
	dist[0] = 0
	byDist[0].add(0)
	for (curDist in byDist.indices) {
		if (curDist % gap == 0) {
			val prefixes = mutableListOf<String>()
			for (c in curDist until curDist + gap) {
				for (v in byDist[c]) {
					if (dist[v] < c) continue
					val take = text[v].length - (c - curDist)
					val prefix = text[v].substring(0, take)
					prefixes.add(prefix)
				}
			}
			prefixes.sort()
			val map = prefixes.withIndex().associate { it.value to (100000 + it.index).toString() }
			for (c in curDist until curDist + gap) {
				for (v in byDist[c]) {
					if (dist[v] < c) continue
					val take = text[v].length - (c - curDist)
					val prefix = text[v].substring(0, take)
					text[v] = map[prefix] + text[v].substring(take)
				}
			}
 		}
		for (v in byDist[curDist]) {
			if (dist[v] < curDist) continue
			for ((label, u) in nei[v]) {
				val newDist = curDist + label.length
				val newText = text[v] + label
				if (newDist < dist[u] || newDist == dist[u] && newText < text[u]) {
					dist[u] = newDist
					text[u] = newText
					var r = rem[v].toLong()
					for (c in label) {
						r = r * 10 + (c - '0')
					}
					rem[u] = (r % M).toInt()
					byDist[newDist].add(u)
				}
			}
		}
	}
	for (i in 1 until n) {
		println(rem[i])
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
