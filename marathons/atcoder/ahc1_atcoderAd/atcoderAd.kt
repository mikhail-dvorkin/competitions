package marathons.atcoder.ahc1_atcoderAd

import java.io.*
import java.util.concurrent.Callable
import kotlin.math.*
import kotlin.random.Random

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(10, 1, false, marathons.utils.atcoder.Visualizer(::solve)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null

fun solve(desired: List<Desired>): List<List<Int>> {
	val random = Random(566)
	val n = desired.size
	val m = desired.sumBy { it.area }.toDouble().pow(0.5).roundToInt()
	val (x1, x2, y1, y2) = List(4) { DoubleArray(n) }
	val (xx1, xx2, yy1, yy2) = List(4) { IntArray(n) }
	var bestScore = 0.0
	var bestPlacing = listOf<List<Int>>()

	fun area(i: Int) = (x2[i] - x1[i]) * (y2[i] - y1[i])
	fun areaInt(i: Int) = (xx2[i] - xx1[i]) * (yy2[i] - yy1[i])
	fun score(i: Int): Double {
		if (desired[i].x !in xx1[i] until xx2[i] || desired[i].y !in yy1[i] until yy2[i]) return 0.0
		val area = areaInt(i)
		return 1 - (1 - minOf(area, desired[i].area).toDouble() / maxOf(area, desired[i].area)).pow(2)
	}
	fun desiredSideIfSquare(i: Int) = desired[i].area.toDouble().pow(0.5)
	fun intersects(z1: Double, z2: Double, t1: Double, t2: Double) = maxOf(z1, t1) < minOf(z2, t2)
	fun intersectsInt(z1: Int, z2: Int, t1: Int, t2: Int) = maxOf(z1, t1) < minOf(z2, t2)
	fun intersects(i: Int, j: Int) = intersects(x1[i], x2[i], x1[j], x2[j]) && intersects(y1[i], y2[i], y1[j], y2[j])
	fun intersectsInt(i: Int, j: Int) = intersectsInt(xx1[i], xx2[i], xx1[j], xx2[j]) && intersectsInt(yy1[i], yy2[i], yy1[j], yy2[j])
	fun lost(i: Int) = desired[i].x + 0.5 < x1[i] || desired[i].x + 0.5 > x2[i] || desired[i].y + 0.5 < y1[i] || desired[i].y + 0.5 > y2[i]
	fun coerce(i: Int) {
		x1[i] = x1[i].coerceAtLeast(0.0)
		x2[i] = x2[i].coerceAtMost(m.toDouble())
		y1[i] = y1[i].coerceAtLeast(0.0)
		y2[i] = y2[i].coerceAtMost(m.toDouble())
	}
	fun grow(i: Int, dir: Int, delta: Double): Double {
		return when (dir) {
			0 -> x1[i].let { x1[i] = (x1[i] - delta).coerceAtLeast(0.0); it - x1[i] }
			1 -> x2[i].let { x2[i] = (x2[i] + delta).coerceAtMost(m.toDouble()); x2[i] - it }
			2 -> y1[i].let { y1[i] = (y1[i] - delta).coerceAtLeast(0.0); it - y1[i] }
			3 -> y2[i].let { y2[i] = (y2[i] + delta).coerceAtMost(m.toDouble()); y2[i] - it }
			else -> error("")
		}
	}
	fun relax(): Double {
		for (i in x1.indices) {
			coerce(i)
			xx1[i] = x1[i].roundToInt()
			xx2[i] = x2[i].roundToInt()
			yy1[i] = y1[i].roundToInt()
			yy2[i] = y2[i].roundToInt()
			if (areaInt(i) == 0) {
				return 0.0
			}
			for (j in 0 until i) if (intersectsInt(j, i)) {
				return 0.0
			}
		}
		val score = x1.indices.sumByDouble { score(it) } / n
		if (score > bestScore) {
			bestScore = score
			bestPlacing = List(n) { listOf(xx1[it], yy1[it], xx2[it], yy2[it]) }
		}
		return score
	}

	fun solveSociophobicInteger() {
		for (i in x1.indices) {
			val d = desired[i]
			val desiredSideIfSquare = desiredSideIfSquare(i).roundToInt()
			val closest = desired.minus(d).minOf { e -> maxOf(abs(d.x - e.x), abs(d.y - e.y)) }
			val size = maxOf(1, minOf(closest / 2, desiredSideIfSquare / 2))
			x1[i] = (desired[i].x + 1 - size).toDouble()
			x2[i] = (desired[i].x + size).toDouble()
			y1[i] = (desired[i].y + 1 - size).toDouble()
			y2[i] = (desired[i].y + size).toDouble()
		}
		relax()
	}
	fun solveSociophobic() {
		for (i in x1.indices) {
			val d = desired[i]
			val desiredSideIfSquare = desiredSideIfSquare(i)
			val closest = desired.minus(d).minOf { e -> maxOf(abs(d.x - e.x), abs(d.y - e.y)) }
			val size = minOf(closest / 2.0, desiredSideIfSquare / 2.0)
			x1[i] = desired[i].x + 0.5 - size
			x2[i] = desired[i].x + 0.5 + size
			y1[i] = desired[i].y + 0.5 - size
			y2[i] = desired[i].y + 0.5 + size
			coerce(i)
		}
		relax()
	}
	fun growGreedy(pushes: Boolean = false, shuffled: Boolean = false) {
		val order = if (shuffled) x1.indices.shuffled(random) else x1.indices.sortedBy { score(it) }
		for (index in order) {
			var delta = desiredSideIfSquare(index)
			while (delta > 0.1) {
				var improved = false
				val areaOriginal = area(index)
				fun candidateNoPush(dir: Int): Double {
					val deltaActual = grow(index, dir, delta)
					val intersects = x1.indices.any { it != index && intersects(it, index) }
					val areaIncrease = if (!intersects && area(index) <= desired[index].area) area(index) - areaOriginal else 0.0
					grow(index, dir, -deltaActual)
					return areaIncrease
				}
				fun candidatePush(dir: Int, undo: Boolean = true): Double {
					val deltaActual = grow(index, dir, delta)
					if (deltaActual isNot delta) {
						grow(index, dir, -deltaActual)
						return 0.0
					}
					val moved = mutableSetOf(index)
					var bad = false
					while (true) {
						val newIntersected = x1.indices.filter { i ->
							if (i in moved) false else moved.any { j -> intersects(i, j) }
						}
						if (newIntersected.isEmpty()) break
						for (i in newIntersected) {
							val delta1 = grow(i, dir, delta)
							if (delta1 isNot delta) {
								bad = true
								grow(i, dir, -delta1)
								break
							}
							grow(i, dir xor 1, -delta)
							moved.add(i)
						}
						if (bad) break
					}
					for (i in moved) {
						if (lost(i)) bad = true
					}
					val areaIncrease = if (!bad && area(index) <= desired[index].area) area(index) - areaOriginal else 0.0
					if (undo) {
						for (i in moved) if (i != index) {
							grow(i, dir, -delta)
							grow(i, dir xor 1, delta)
						}
						grow(index, dir, -delta)
					}
					return areaIncrease
				}
				fun candidate(dir: Int) = if (pushes) candidatePush(dir) else candidateNoPush(dir)
				fun use(dir: Int) = if (pushes) candidatePush(dir, undo = false) else grow(index, dir, delta)
				val (best, bestDir) = List(4) { dir -> candidate(dir) to dir }.maxByOrNull { it.first }!!
				if (best > 0) {
					improved = true
					use(bestDir)
					relax()
				}
				if (!improved) delta /= 2.0
			}
		}
		relax()
	}

	try {
		solveSociophobicInteger()
		solveSociophobic()
		repeat(2) {
			growGreedy()
			growGreedy(pushes = true)
		}
	} catch (_: TimeOutException) {
	}
	if (!SUBMIT) print("$bestScore ")
	return bestPlacing
}

data class Desired(val x: Int, val y: Int, val area: Int)

private infix fun Double.isNot(other: Double) = abs(this - other) > 1e-9

fun solve(`in`: BufferedReader, out: Writer) {
	fun readLn() = `in`.readLine()!!
	fun readInt() = readLn().toInt()
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }

	val desired = List(readInt()) { val (x, y, area) = readInts(); Desired(x, y, area) }
	val ans = solve(desired)
	ans.forEach { out.write(it.joinToString(" ")); out.write(System.lineSeparator()) }
	out.close()
}

class TimeOutException : RuntimeException()

fun main() {
	@Suppress("UNNECESSARY_SAFE_CALL")
	EVALUATOR?.call() ?: solve(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
