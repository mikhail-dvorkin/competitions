package facebook.y2020.round1

import java.util.*

private fun solve(M: Int = 1_000_000_007): Int {
	val (n, k) = readInts()
	val (L, w, h) = List(3) { readInts().toMutableList().also { list ->
		val (a, b, c, d) = readInts()
		for (i in k until n) {
			list.add(((a.toLong() * list[i - 2] + b.toLong() * list[i - 1] + c) % d + 1).toInt())
		}
	}}
	data class Group(val xFrom: Int, val xTo: Int, val vertical: Long)
	fun perimeter(g: Group): Long = 2 * (g.xTo - g.xFrom) + g.vertical
	val groups = TreeMap<Int, Group>()
	var ans = 1
	var perimeter = 0L
	for (i in h.indices) {
		var new = Group(L[i], L[i] + w[i], 2L * h[i])
		while (true) {
			var g = groups.floorEntry(new.xFrom)?.value
			if (g == null || g.xTo < new.xFrom) g = groups.ceilingEntry(new.xFrom)?.value
			if (g != null && g.xFrom > new.xTo) g = null
			if (g != null && new.xFrom > g.xTo) g = null
			if (g == null) break
			perimeter -= perimeter(g)
			groups.remove(g.xFrom)
			new = Group(minOf(g.xFrom, new.xFrom), maxOf(g.xTo, new.xTo), g.vertical + new.vertical - 2 * h[i])
		}
		groups[new.xFrom] = new
		perimeter += perimeter(new)
		ans = ((ans.toLong() * (perimeter % M)) % M).toInt()
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
