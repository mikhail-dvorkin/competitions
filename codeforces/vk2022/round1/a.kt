package codeforces.vk2022.round1

import kotlin.math.abs

private fun solve() {
	val (w, h, hei) = readInts()
	val (x1, y1, x2, y2) = readInts()
	val pos = listOf(
		x1 + x2 + abs(y1 - y2),
		(w - x1) + (w - x2) + abs(y1 - y2),
		y1 + y2 + abs(x1 - x2),
		(h - y1) + (h - y2) + abs(x1 - x2),
	)
	println(pos.min() + hei)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
