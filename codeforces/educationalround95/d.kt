package codeforces.educationalround95

import java.util.*

fun main() {
	val (n, q) = readInts()
	val pIn = readInts().sorted()
	val p = TreeSet<Int>(pIn)
	val segments = TreeMap<Int, Int>()
	fun addSegment(segment: Int) {
		segments.putIfAbsent(segment, 0)
		segments[segment] = segments.getOrDefault(segment, 0) + 1
	}
	fun removeSegment(segment: Int) {
		segments[segment] = segments[segment]!! - 1
		if (segments[segment] == 0) segments.remove(segment)
	}
	for (i in 1 until pIn.size) {
		val segment = pIn[i] - pIn[i - 1]
		addSegment(segment)
	}
	fun ans() {
		if (p.size <= 2) return println(0)
		println(p.last() - p.first() - segments.lastKey())
	}
	ans()
	repeat(q) {
		val (t, x) = readInts()
		val xPrev = p.floor(x - 1)
		val xNext = p.ceiling(x + 1)
		if (t == 0) {
			if (xPrev != null) removeSegment(x - xPrev)
			if (xNext != null) removeSegment(xNext - x)
			if (xPrev != null && xNext != null) addSegment(xNext - xPrev)
			p.remove(x)
		} else {
			if (xPrev != null) addSegment(x - xPrev)
			if (xNext != null) addSegment(xNext - x)
			if (xPrev != null && xNext != null) removeSegment(xNext - xPrev)
			p.add(x)
		}
		ans()
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
