package codeforces.globalround16

private fun solve() {
	val (n, m) = readInts()
	val points = readInts().sorted()
	val segments = List(m) { readInts().let { it[0] to it[1] } }
	val groups = List(n + 1) { mutableListOf<Pair<Int, Int>>() }
	for (segment in segments) {
		val index = (-1..points.size).binarySearch { points[it] >= segment.first }
		if (index < points.size) {
			if (points[index] <= segment.second) continue
		}
		groups[index].add(segment)
	}
	var ifEats = 0L
	var ifGives = 0L
	val inf = Long.MAX_VALUE / 8
	for (k in groups.indices) {
		groups[k].sortBy { it.first }
		val g = mutableListOf<Pair<Int, Int>>()
		for (segment in groups[k]) {
			if (g.isEmpty()) {
				g.add(segment)
				continue
			}
			if (g.last().first == segment.first && g.last().second <= segment.second) continue
			while (g.isNotEmpty() && g.last().first <= segment.first && segment.second <= g.last().second) {
				g.pop()
			}
			g.add(segment)
		}
		val pPrev = if (k == 0) -inf else points[k - 1].toLong()
		val pNext = if (k == groups.lastIndex) inf else points[k].toLong()
		var newIfEats = inf
		var newIfGives = inf
		for (i in 0..g.size) {
			val comeLeft = if (i == 0) pPrev else g[i - 1].first.toLong()
			val comeRight = if (i == g.size) pNext else g[i].second.toLong()
			val leftBest = minOf(ifEats + 2 * (comeLeft - pPrev), ifGives + comeLeft - pPrev)
			val possibleIfEats = leftBest + pNext - comeRight
			newIfEats = minOf(newIfEats, possibleIfEats)
			val possibleIfGives = leftBest + 2 * (pNext - comeRight)
			newIfGives = minOf(newIfGives, possibleIfGives)
		}
		ifEats = newIfEats
		ifGives = newIfGives
	}
	println(minOf(ifEats, ifGives))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}
private fun <T> MutableList<T>.pop() = removeAt(lastIndex)
