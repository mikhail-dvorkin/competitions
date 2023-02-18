package marathons.atcoder.ahc15_halloweenCandy

import kotlin.math.*
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val SUBMIT = System.getProperty("sun.java.command", "").contains("MainKt")
const val COLORS = 3
val DY = intArrayOf(1, 0, -1, 0)
val DX = intArrayOf(0, 1, 0, -1)
val random = Random(566)
const val DIR = "BRFL"

const val MAGIC_WORLDS = 64
const val MAGIC_DEPTH = 2

private fun play() {
	if (!SUBMIT) {
		System.setIn(java.io.File("input.txt").inputStream())
		System.setOut(java.io.PrintStream("output.txt"))
	}
	val future = readInts().map { it - 1 }
	val n = sqrt(future.size.toDouble()).roundToInt()
	require(future.size == n * n)
	var field = List(n) { IntArray(n) { -1 } }
	repeat(future.size) { move ->
		val new = readInt() - 1
		val (yNew, xNew) = emptyNumber(field, new)
		field[yNew][xNew] = future[move]
//		val tilt = future[move]
		val tilt = bestTilt(field, move, future.drop(move + 1).take(MAGIC_DEPTH))
		println(DIR[tilt])
		field = tilted(field, tilt)
	}
	if (!SUBMIT) {
		System.err.println(score(field))
	}
}

fun bestTilt(field: List<IntArray>, move: Int, future: List<Int>): Int {
	val scoresByTilt = DoubleArray(4)
	for (world in 0 until MAGIC_WORLDS) {
		val futurePosition = IntArray(future.size) { random.nextInt(field.size * field.size - move - it - 1) }
		fun scores(field: List<IntArray>, k: Int): DoubleArray = DoubleArray(scoresByTilt.size) { d ->
			val f = tilted(field, d)
			if (k == future.size) {
				score(f)
			} else {
				val (yNew, xNew) = emptyNumber(f, futurePosition[k])
				f[yNew][xNew] = future[k]
				val scoresNext = scores(f, k + 1)
				scoresNext.max()
			}
		}
		val scores = scores(field, 0)
		for (d in scoresByTilt.indices) scoresByTilt[d] += scores[d]
	}
	return scoresByTilt.withIndex().maxBy { it.value }.index
}


private fun emptyNumber(field: List<IntArray>, number: Int): Pair<Int, Int> {
	var m = number
	for (y in field.indices) for (x in field.indices) {
		if (field[y][x] != -1) continue
		if (m-- == 0) return y to x
	}
	error(toString(field) to number)
}

var mark: List<BooleanArray>? = null

private fun score(field: List<IntArray>): Double {
	if (mark == null) {
		mark = List(field.size) { BooleanArray(field.size) }
	} else {
		for (row in mark!!) row.fill(false)
	}
	var compSize = 0
	fun dfs(y: Int, x: Int) {
		compSize++
		mark!![y][x] = true
		for (d in 0 until 4) {
			val yy = y + DY[d]
			val xx = x + DX[d]
			if (yy !in field.indices || xx !in field.indices || mark!![yy][xx] || field[yy][xx] != field[y][x]) continue
			dfs(yy, xx)
		}
	}

	var sumCompSquares = 0
	val colorCount = IntArray(COLORS)
	for (y in field.indices) for (x in field.indices) {
		if (field[y][x] == -1) continue
		colorCount[field[y][x]]++
		if (mark!![y][x]) continue
		compSize = 0
		dfs(y, x)
		sumCompSquares += compSize * compSize
	}
	return 1e6 * sumCompSquares / colorCount.sumOf { it * it }
}

private fun toString(field: List<IntArray>) = field.map { it.contentToString() }

private fun tilted(field: List<IntArray>, dir: Int): List<IntArray> {
	val result = List(field.size) { IntArray(field.size) { -1 } }
	if (dir % 2 == 0) {
		for (x in field.indices) {
			var k = 0
			if (dir >= 2) {
				for (y in field.indices) if (field[y][x] != -1) result[k++][x] = field[y][x]
			} else {
				for (y in field.indices.reversed()) if (field[y][x] != -1) result[field.size - 1 - k++][x] = field[y][x]
			}
		}
	} else {
		for (y in field.indices) {
			var k = 0
			if (dir >= 2) {
				for (x in field.indices) if (field[y][x] != -1) result[y][k++] = field[y][x]
			} else {
				for (x in field.indices.reversed()) if (field[y][x] != -1) result[y][field.size - 1 - k++] = field[y][x]
			}
		}
	}
	return result
}

@OptIn(ExperimentalTime::class)
fun main() {
	val time = measureTime { play() }
	if (!SUBMIT) System.err.println(time)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
