package codeforces.kotlinheroes3

import java.util.*

private fun solve() {
	val (n, m) = readInts()
	data class Segment(val start: Int, val end: Int, val id: Int)
	val segments = List(n) { val data = readInts(); Segment(data[0], data[1], it) }
	val open = segments.sortedBy { it.start }
	val ans = MutableList(n) { 0 }
	var time = 0
	var i = 0
	val toWatch = TreeSet<Long>()
	var worstGap = 0L
	while (true) {
		if (toWatch.isEmpty()) {
			if (i == n) break
			time = open[i].start
		}
		while (i < n && open[i].start == time) {
			toWatch.add(open[i].end * n.toLong() + open[i].id)
			i++
		}
		for (temp in 0 until m) {
			if (toWatch.isEmpty()) break
			val v = toWatch.first()!!
			val t = v / n
			val id = (v % n).toInt()
			worstGap = maxOf(worstGap, time - t)
			ans[id] = time
			toWatch.remove(v)
		}
		time++
	}
	println(worstGap)
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
