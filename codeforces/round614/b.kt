package codeforces.round614

import kotlin.math.abs

fun main() {
	val (x0, y0, ax, ay, bx, by) = readLongs()
	val (xs, ys, t) = readLongs()
	val points = mutableListOf(x0 to y0)
	while (true) {
		val (x, y) = ax * points.last().first + bx to ay * points.last().second + by
		if (xs + ys + t < x + y) break
		points.add(x to y)
	}
	val best = points.indices.mapNotNull { i ->
		points.indices.mapNotNull { j ->
			(abs(j - i) + 1).takeIf { dist(xs to ys, points[i]) + dist(points[i], points[j]) <= t }
		}.max()
	}.max() ?: 0
	println(best)
}

private fun dist(a: Pair<Long, Long>, b: Pair<Long, Long>): Long = abs(a.first - b.first) + abs(a.second - b.second)

private operator fun <E> List<E>.component6(): E = get(5)
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
