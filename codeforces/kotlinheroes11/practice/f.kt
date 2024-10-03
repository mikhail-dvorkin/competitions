package codeforces.kotlinheroes11.practice

import kotlin.math.abs

private fun solve(allColors: String = "BGRY") {
	val (_, q) = readInts()
	val colors = readStrings().map { s -> s.sumOf { 1 shl allColors.indexOf(it) } }
	val inf = Int.MAX_VALUE / 4
	val rightmost = List(16) { IntArray(colors.size) { -inf } }
	for (i in colors.indices) {
		if (i > 0) for (j in rightmost.indices) rightmost[j][i] = rightmost[j][i - 1]
		rightmost[colors[i]][i] = i
	}
	val leftmost = List(rightmost.size) { IntArray(colors.size) { inf } }
	for (i in colors.indices.reversed()) {
		if (i < colors.lastIndex) for (j in leftmost.indices) leftmost[j][i] = leftmost[j][i + 1]
		leftmost[colors[i]][i] = i
	}
	fun solveQuery(x: Int, y: Int): Int {
		if (colors[x] and colors[y] > 0) {
			return abs(x - y)
		}
		val candidates = rightmost.indices.filter { it != colors[x] && it != colors[y] }
			.flatMap { listOf(rightmost[it][y], leftmost[it][x]) }
		val result = candidates.minOf { abs(x - it) + abs(y - it) }
		return result.takeIf { it < inf } ?: -1
	}
	repeat(q) {
		val (x, y) = readInts().map { it - 1 }.sorted()
		out.appendLine(solveQuery(x, y).toString())
	}
}

fun main() = repeat(readInt()) { solve() }.also { out.close() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private val out = System.out.bufferedWriter()
