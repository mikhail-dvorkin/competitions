package codeforces.round606

import kotlin.math.*

fun main() {
	val n = readInt()
	val a = readInts().sorted()
	val groupBy = a.groupBy { it }.toList().sortedByDescending { it.second.size }
	val b = groupBy.map { it.second.size }
	val m = floor(sqrt(n.toDouble())).toInt()
	var bestArea = 0
	var bestI = 0
	var bSelf = b.size
	var bSum = 0
	for (i in 1..m) {
		while (bSelf > 0 && b[bSelf - 1] <= i) {
			bSelf--
			bSum += b[bSelf]
		}
		var area = bSelf * i + bSum
		if (area < i * i) continue
		area -= area % i
		if (area > bestArea) {
			bestArea = area
			bestI = i
		}
	}
	val h = bestI
	val w = bestArea / h
	println("$bestArea\n$h $w")
	val f = List(h) { IntArray(w) }
	var x = 0
	var y = 0
	for (group in groupBy) {
		repeat(minOf(group.second.size, h)) {
			f[y][x % w] = group.first
			x++; y++
			if (y == h) {
				y = 0
				x -= h - 1
			}
			if (y == 0 && x == w) {
				val sb = StringBuilder()
				for (i in 0 until h) {
					sb.append(f[i].joinToString(" "))
					sb.append("\n")
				}
				return print(sb)
			}
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
