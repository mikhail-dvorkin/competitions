package codeforces.round606

import kotlin.math.sqrt

fun main() {
	readLn()
	val a = readInts()
	val groups = a.groupBy { it }.toList().sortedByDescending { it.second.size }
	val b = groups.map { it.second.size }
	var bSelf = b.size
	var bSum = 0
	val (h, w) = (1..sqrt(a.size.toDouble()).toInt()).map { h ->
		while (bSelf > 0 && b[bSelf - 1] <= h) bSum += b[--bSelf]
		h to ((bSelf + bSum / h).takeIf { it >= h } ?: 0)
	}.maxByOrNull { it.first * it.second }!!
	println("${h * w}\n$h $w")
	val f = List(h) { IntArray(w) }
	var x = 0
	for (group in groups) repeat(minOf(group.second.size, h)) {
		f[x % h][(x % h + x / h) % w] = group.first
		if (++x == h * w) return f.forEach { println(it.joinToString(" ")) }
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
