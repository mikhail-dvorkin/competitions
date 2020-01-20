package codeforces.round614

import kotlin.math.abs

fun main() {
	val (x0, y0, ax, ay, bx, by) = readLongs()
	val (xs, ys, t) = readLongs()
	val points = mutableListOf(x0 to y0)
	while (true) {
		val last = points.last()
		val (xt, yt) = ax * last.first + bx to ay * last.second + by
		if (xs + ys + t < xt + yt) break
		points.add(xt to yt)
	}
	var best = 0
	for (i in points.indices) {
		for (j in i until points.size) {
			val dist = minOf(dist(xs to ys, points[i]), dist(xs to ys, points[j])) + dist(points[i], points[j])
			if (dist <= t) best = maxOf(best, j - i + 1)
		}
	}
	println(best)
}

fun dist(a: Pair<Long, Long>, b: Pair<Long, Long>): Long {
	return abs(a.first - b.first) + abs(a.second - b.second)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
private operator fun <E> List<E>.component6(): E {
	return this[5]
}
