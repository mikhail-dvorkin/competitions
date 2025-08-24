package marathons.atcoder.ahc52_singleControllerMultipleRobots //TESTING

import java.io.BufferedReader
import java.io.File
import java.io.PrintWriter
import java.util.BitSet
import kotlin.math.abs

private val TO_EVAL = (0 until 100)//listOf(118)//
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (2_000 - 250)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }
private class TimeOutException : RuntimeException()

private fun solve(k: Int, posInitList: List<List<Int>>, ver: List<BooleanArray>, hor: List<BooleanArray>): List<String> {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()
	val n = ver.size
	val m = posInitList.size
	var mode = -1

	fun canMove(y: Int, x: Int, dy: Int, dx: Int): Boolean {
		val yy = y + dy
		val xx = x + dx
		if (yy !in 0 until n || xx !in 0 until n) return false
		return if (dy == 0) !ver[y][minOf(x, xx)] else !hor[minOf(y, yy)][x]
	}
	infix fun Int.with(that: Int) = this * n + that
	fun decode(code: Int) = (code / n) to (code % n)
	fun manhattanDistFromCenter(code: Int): Int {
		val (y, x) = decode(code)
		return abs(y - n / 2) + abs(x - n / 2)
	}
	fun manhattanDist(code1: Int, code2: Int): Int {
		val (y1, x1) = decode(code1)
		val (y2, x2) = decode(code2)
		return abs(y1 - y2) + abs(x1 - x2)
	}
	val posInit = posInitList.map { it[0] with it[1] }
	val cells = (0 until n * n).toList()
	val cellsFromFar = cells.sortedByDescending { manhattanDistFromCenter(it) }

	fun walk(cell: Int, d: Int, dist: Int): Int {
		var (y, x) = decode(cell)
		val dy = DY[d]
		val dx = DX[d]
		for (i in 0 until dist) {
			if (!canMove(y, x, dy, dx)) break
			y += dy
			x += dx
		}
		return y with x
	}

	var swipeLen = -1
	var swipeTimes = -1
	var walkings = mutableListOf<Int>()

	fun swipe(yInit: Int, xInit: Int, dy0: Int, dx0: Int, dy1: Int, dx1: Int): BitSet {
		var y = yInit
		var x = xInit
		val swiped = BitSet()
		swiped.set(y with x)
		fun tryMove(dy: Int, dx: Int): Boolean {
			if (!canMove(y, x, dy, dx)) return false
			y += dy
			x += dx
			swiped.set(y with x)
			return true
		}
		for (time in 0 until swipeTimes) {
			if (time > 0) tryMove(dy1, dx1)
			val alternate = if (time % 2 == 0) 1 else -1
			for (i in 0 until swipeLen) {
				if (!tryMove(dy0 * alternate, dx0 * alternate)) break
			}
		}
		return swiped
	}

	val walkingReachable = MutableList(n * n) { mapOf<Int, String>() }
	fun researchWalkings() {
		for (cell in posInit) {
			checkTimeLimit()
			var reachable = mutableMapOf(cell to "")
			for (j in walkings) {
				val nr = mutableMapOf<Int, String>()
				for ((c, way) in reachable) {
					nr[c] = way + "S"
					for (d in 0 until 4) {
						val cc = walk(c, d, j)
						nr[cc] = way + DIR[d]
					}
				}
				reachable = nr
			}
			walkingReachable[cell] = reachable
		}
	}

	var lastRobots = -1
	fun greedyA(): List<String> {
		lastRobots = -1
		val swipes = List(n) { y -> List(n) { x -> List(8) { e ->
			checkTimeLimit()
			val d0 = e % 4
			val d1 = (e + if (e >= 4) 1 else 3) % 4
			swipe(y, x, DY[d0], DX[d0], DY[d1], DX[d1])
		} } }
//		researchWalkings()
		val alive = (0 until m).toMutableSet()
		val swiped = BitSet()
		for (cell in posInit) swiped.set(cell)
		val selected = mutableListOf<Triple<Int, Int, Int>>()
		val selectedRobots = mutableListOf<Int>()
		for (cell in cellsFromFar) {
			if (swiped[cell]) continue
			checkTimeLimit()

			val reachable = mutableSetOf<Int>()
			for (robot in alive) {
				reachable.addAll(walkingReachable[posInit[robot]].keys)
			}

			var best = BitSet()
			var bestScore = -1
			var bestP: Triple<Int, Int, Int>? = null
			for (y in 0 until n) for (x in 0 until n) for (e in 0 until 8) {
				if (y with x !in reachable) continue
				val sw = swipes[y][x][e]
				if (!sw[cell]) continue
				val score = cells.count { cell -> sw[cell] && !swiped[cell] }
				if (score > bestScore) {
					bestScore = score
					best = sw
					bestP = Triple(y, x, e)
				}
			}
			if (bestScore == -1) return listOf()
			swiped.or(best)
			selected.add(bestP!!)
			val bestCell = bestP.first with bestP.second
			val robots = alive.filter { robot -> bestCell in walkingReachable[posInit[robot]] }
			val robot = when (mode % 2) {
				0 -> robots.first()
//				1 -> robots.maxBy { robot ->
//					manhattanDist(posInit[robot], bestCell)
//				}
//				2 -> robots.last()
				else -> robots.minBy { robot ->
					manhattanDist(posInit[robot], bestCell)
				}
			}
//			bfsDist[posInit[robot]][bestCell]
			alive.remove(robot)
			selectedRobots.add(robot)
		}
		lastRobots = selected.size
		info { selected.size }

		val buttons = List(k) { CharArray(m) { 'S' } }
		val pressed = mutableListOf<Int>()
		for (jIndex in walkings.indices) {
			repeat(walkings[jIndex]) { pressed.add(jIndex) }
		}
		for (time in 0 until swipeTimes) {
			if (time > 0) pressed.add(k - 1)
			repeat(swipeLen) {
				pressed.add(k - (if (time % 2 == 0) 3 else 2))
			}
		}
		for (i in selected.indices) {
			val tr = selected[i]
			val e = tr.third
			val robot = selectedRobots[i]
			val d0 = e % 4
			val d1 = (e + if (e >= 4) 1 else 3) % 4
			val w = walkingReachable[posInit[robot]][tr.first with tr.second]!!
			for (j in w.indices) buttons[j][robot] = w[j]
			buttons[k - 3][robot] = DIR[d0]
			buttons[k - 2][robot] = DIR[d0 xor 2]
			buttons[k - 1][robot] = DIR[d1]
		}
		val ans = mutableListOf<String>()
		for (row in buttons) ans.add(row.joinToString(" "))
		for (p in pressed) ans.add(p.toString())
		return ans
	}

	var ans = listOf<String>()//greedyA()

	try {
		mode = n
		walkings = mutableListOf(8, 8, 7, 7, 6, 6, 5); researchWalkings()
		while (true) {
			swipeTimes = mode / 2
			swipeLen = mode - mode / 2
			if (mode <= n - 4 && walkings.last() == 5) { walkings = mutableListOf(8, 7, 7, 6, 5, 3, 1); researchWalkings() }
			val a = greedyA()
			if (a.isNotEmpty()) {
				ans = a.also {
					info { "OK$mode" }
				}
				if (lastRobots <= k - 2 && mode == n) mode--
				if (lastRobots <= k - 2) mode--
				mode--
			} else {
				mode++
				val j = walkings.indices.last { walkings[it] != 0 }
				if (j == 0) break
				walkings[j] = 0
				researchWalkings()
			}
		}
	} catch (_: TimeOutException) {
	}
	log?.close()
	return ans
}

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()
	val (n, m, k) = `in`.readInts()
	val posInit = List(m) { `in`.readInts() }
	val (ver, hor) = listOf(n, n - 1).map { size -> List(size) { `in`.readln().map { it == '1' }.toBooleanArray() } }
	out.println(solve(k, posInit, ver, hor).joinToString("\n"))
	out.close()
	return toVisualize
}

val DY = intArrayOf(0, 1, 0, -1)
val DX = intArrayOf(1, 0, -1, 0)
const val DIR = "RDLU"

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
