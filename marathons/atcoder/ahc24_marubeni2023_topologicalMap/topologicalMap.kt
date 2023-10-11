package marathons.atcoder.ahc24_marubeni2023_topologicalMap

import java.io.BufferedReader
import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.roundToInt

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(marathons.utils.atcoder.atcoderVisualizer(::solveIO)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
const val TIME_LIMIT = 2000 - 150
var timeStart = 0L
// TODO timeLimit *= Template._localTimeCoefficient
fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

fun solve(f: List<IntArray>, m: Int, toVisualize: MutableList<Any>?): List<IntArray> {
	val pSearchSpace = 4
	val pOutsideThreshold = 3 // TODO 16?, use borders better
	val n = f.size
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)

	fun field(y: Int, x: Int) = f.getOrNull(y)?.getOrNull(x) ?: 0
	fun isBorder(y1: Int, x1: Int, y2: Int, x2: Int) = if (y1 == y2) {
		val x0 = minOf(x1, x2)
		field(y1 - 1, x0) != field(y1, x0)
	} else {
		val y0 = minOf(y1, y2)
		field(y0, x1 - 1) != field(y0, x1)
	}

//	val areaNei = List(m + 1) { mutableListOf<Int>() }
//	for (y in f.indices) for (x in f.indices) {
//		val c1 = f[y][x]
//		for (d in 0 until 2) {
//			val yy = y + DY[d]; val xx = x + DX[d]
//			if (yy >= n || xx >= n) continue
//			val c2 = f[yy][xx]
//			if (c1 == c2 || c1 in areaNei[c2]) continue
//			areaNei[c1].add(c2)
//			areaNei[c2].add(c1)
//		}
//	}

	val degree = List(n + 1) { IntArray(n + 1) }
	var realPointCount = 0
	val pointId = List(n + 1) { IntArray(n + 1) { -1 } }
	val (point6Y, point6X) = List(2) { IntArray((n + 1) * (n + 1)) }
	for (y in 0..n) for (x in 0..n) {
		for (d in 0 until 4) if (isBorder(y, x, y + DY[d], x + DX[d])) {
			degree[y][x]++
		}
		if (degree[y][x] >= 3) {
			pointId[y][x] = realPointCount
			point6Y[realPointCount] = 6 * y
			point6X[realPointCount] = 6 * x
			realPointCount++
		}
	}

	tailrec fun followBorder(y: Int, x: Int, d: Int): Pair<Int, Int> {
		val yy = y + DY[d]; val xx = x + DX[d]
		if (pointId[yy][xx] != -1) return pointId[yy][xx] to d
		val dds = (0 until 4).filter { dd -> isBorder(yy, xx, yy + DY[dd], xx + DX[dd]) }
		require(dds.size == 2 && (d xor 2) in dds)
		return followBorder(yy, xx, dds.first { it != d xor 2 })
	}

	val pointNei = MutableList(realPointCount) { IntArray(4) { -1 } }
	for (y in 0..n) for (x in 0..n) {
		val p1 = pointId[y][x]
		if (p1 == -1) continue
		for (d in 0 until 4) {
			if (!isBorder(y, x, y + DY[d], x + DX[d])) continue
			if (pointNei[p1][d] != -1) continue
			val (p2, d2) = followBorder(y, x, d)
			require(p2 != p1)
			if (p2 !in pointNei[p1]) {
				pointNei[p1][d] = p2
				continue
			}
			pointNei[p1][d] = pointNei.size
			pointNei[p2][d2 xor 2] = pointNei.size
			point6Y[pointNei.size] = (point6Y[p1] + point6Y[p2]) / 2
			point6X[pointNei.size] = (point6X[p1] + point6X[p2]) / 2
			pointNei.add(intArrayOf(p1, p2, -1, -1)) // TODO calc better directions
		}
	}
//	log { degree.joinToString("\n") { it.contentToString() } }
	info { "$realPointCount→${pointNei.size} points" }

	val realPoints = (0..n).cartesianSquare()
		.filter { pointId[it.first][it.second] != -1 }
	val pointInit = realPoints
		.minBy { abs(it.first - n / 2) + abs(it.second - n / 2) }
		.let { pointId[it.first][it.second] }

	val inf = Int.MAX_VALUE / 2
	val queuePoints = IntArray(pointNei.size)
	fun bfsPoints() {
		val dist = IntArray(pointNei.size) { inf }
		queuePoints[0] = pointInit
		dist[pointInit] = 0
		var low = 0
		var high = 1
		while (low < high) {
			val v = queuePoints[low++]
			for (u in pointNei[v]) if (u != -1 && dist[u] == inf) {
				queuePoints[high++] = u
				dist[u] = dist[v] + 1
			}
		}
		require(high == pointNei.size)
	}
	bfsPoints()

	fun selectOrder(): MutableList<Int> {
		val order = mutableListOf(pointInit)
		val orderSet = order.toMutableSet()
		val friendStat = IntArray(5) // TESTING
		while (true) {
			val v = queuePoints
				.filter { it !in orderSet }
				.maxByOrNull { v -> pointNei[v].count { it in orderSet } }
				?: break
			order.add(v)
			orderSet.add(v)
			friendStat[pointNei[v].count { it in orderSet }]++ // TESTING
		}
		info { "fr" + friendStat.drop(1) }
//		log { order.map { hex(it) } }
		return order
	}
	val order = selectOrder()
//	val order = queuePoints

	fun hex(v: Int) = if (v < 0) v.toString() else order.indexOf(v).also {
		require(it != -1)
	}.toString(36)
	fun hexWithNei(v: Int) = if (v < 0) v.toString() else hex(v) + ":" + pointNei[v].joinToString(",", transform = ::hex).replace("-1", "_")
	fun aux(hex: String): String = if (hex.length == 1) aux("0$hex") else "⁰¹²³⁴⁵⁶⁷⁸⁹".getOrElse(hex[0].code - '0'.code) {'^'} + "" + hex[1]
	fun show(v: Int, withNei: Boolean): String {
		if (v <= -10) return aux(hex(-10 - v))
		if (v == -2) return "@"
		if (v == -1) return ""
		return if (withNei) hexWithNei(v) else hex(v)
	}
	fun showTable(selector: (Pair<Int, Int>) -> String) =
		List(n + 1) { y -> List(n + 1) { x -> selector(y to x) }.joinToString(" ") }.joinToStringTrimmed()

//	log { List(n + 1) { y -> IntArray(n + 1) { x -> pointId[y][x].takeIf { it != -1 }?.let { dist[it] } ?: -1 } }.joinToString("\n") { it.joinToString("\t") } }
	log { showTable { p -> pointId[p.first][p.second].takeIf { it != -1 }?.let { hex(it).padStart(2) } ?: if (degree[p.first][p.second] == 2) " *" else " ." } }
	log { List(pointNei.size) { hexWithNei(it) }.sorted().joinToString("\t") }

	fun encodeLine(y: Int, x: Int, d: Int): Int {
		if (d >= 2) return encodeLine(y + DY[d], x + DX[d], d xor 2)
		return ((y * (n + 1) + x) shl 1) or d
	}
	fun decodeLine(code: Int): Triple<Int, Int, Int> {
		val d = code and 1
		val c = code shr 1
		return Triple(c / (n + 1), c % (n + 1), d)
	}

	/**
	 * 0..  point
	 * -1   empty
	 * -2   used in path
	 * ..-10 reserved for neighbour
	 */
	val a = List(n + 1) { IntArray(n + 1) { -1 } }
	val (ay, ax) = List(2) { IntArray(pointNei.size) { -1 } }
	val aTo = List(n + 1) { List(n + 1) { IntArray(4) { -1 } } }
	val line = BooleanArray((n + 1) * (n + 1) * 2)
	data class Path(val path: List<Int>, val dFirst: Int, val dLast: Int, val idSource: Int, val idDest: Int)

	fun addPath(path: Path) {
		aTo[ay[path.idSource]][ax[path.idSource]][path.dFirst] = path.idDest
		aTo[ay[path.idDest]][ax[path.idDest]][path.dLast xor 2] = path.idSource
		for (code in path.path) {
			line[code] = true
			val (y, x, d) = decodeLine(code)
			for (e in 0..1) {
				val yy = y + DY[d] * e;
				val xx = x + DX[d] * e
				if (a[yy][xx] < 0) a[yy][xx] = -2
			}
		}
	}
	fun removePath(path: Path) {
		aTo[ay[path.idSource]][ax[path.idSource]][path.dFirst] = -1
		aTo[ay[path.idDest]][ax[path.idDest]][path.dLast xor 2] = -1
		for (code in path.path) {
			line[code] = false
			val (y, x, d) = decodeLine(code)
			for (e in 0..1) {
				val yy = y + DY[d] * e;
				val xx = x + DX[d] * e
				if (a[yy][xx] == -2) a[yy][xx] = -1
			}
		}

	}
	fun addPoint(v: Int, y: Int, x: Int) {
		require(a[y][x] == -1 || a[y][x] == -10 - v)
		ay[v] = y; ax[v] = x
		a[y][x] = v
	}
	fun removePoint(v: Int) {
		a[ay[v]][ax[v]] = -1
		ay[v] = -1; ax[v] = -1
	}

	fun okDir(yFrom: Int, xFrom: Int, d: Int, idTo: Int): Boolean {
		val idFrom = a[yFrom][xFrom]
		require(pointNei[idFrom].indexOf(idTo) != -1)
		if (aTo[yFrom][xFrom][d] != -1) return false
		val aThere = a[yFrom + DY[d]][xFrom + DX[d]]
		if (aThere != -1 && aThere != -10 - idTo && aThere != -10 - idFrom && aThere != idTo) return false
		fun bad(id1: Int, id2: Int): Boolean {
			val aSpace = (aTo[yFrom][xFrom].indexOf(id2) - aTo[yFrom][xFrom].indexOf(id1)).mod(4) - 1
			var i = pointNei[idFrom].indexOf(id1)
			var between = 0
			while (true) {
				i = (i + 1) % 4
				val id = pointNei[idFrom][i]
				if (id == id2) break
				if (id != -1) between++
			}
			return between > aSpace
		}

		var bad = false
		aTo[yFrom][xFrom][d] = idTo
		for (id in aTo[yFrom][xFrom]) if (id != -1 && id != idTo) {
			if (bad(id, idTo) || bad(idTo, id)) bad = true
		}
		aTo[yFrom][xFrom][d] = -1
		return !bad
	}
	val mark1 = List(n + 1) { IntArray(n + 1) }
	var time1 = 0
	var count1 = 0
	val toSee1 = mutableSetOf<Int>()
	var allow1 = 0
	fun dfs1(y: Int, x: Int) {
		mark1[y][x] = time1
		if (count1++ >= pOutsideThreshold) return
		for (d in 0 until 4) {
			val yy = y + DY[d]; val xx = x + DX[d]
			if (yy !in 0..n || xx !in 0..n) {
				count1 = pOutsideThreshold
				return
			}
			if (a[yy][xx] != -1 && a[yy][xx] != allow1) {
				toSee1.remove(a[yy][xx])
				toSee1.remove(-10 - a[yy][xx])
				continue
			}
			if (mark1[yy][xx] == time1) continue
			dfs1(yy, xx)
		}
	}
	fun canLockHere(y: Int, x: Int, v: Int): Boolean {
		toSee1.clear()
		toSee1.addAll(pointNei[v].filter { it != -1 })
		allow1 = -10 - v
		time1++; count1 = 0
		dfs1(y, x)
		return toSee1.isEmpty() || count1 >= pOutsideThreshold
	}

	val okDir = List(n + 1) { IntArray(n + 1) }
	fun calcOkDirs(): Boolean {
		for (y in a.indices) for (x in a[y].indices) if (a[y][x] <= -10) a[y][x] = -1
		for (v in order) {
			val yv = ay[v]; val xv = ax[v]
			if (yv == -1) break
			okDir[yv][xv] = 0xFFFF
		}
		do {
			var changed = false
			for (v in order) {
				val yv = ay[v]; val xv = ax[v]
				if (yv == -1) break
				var newOkDir = 0
				for (i in 0 until 4) {
					val u = pointNei[v][i]
					if (u == -1 || u in aTo[yv][xv]) continue
					var count = 0
					for (d in 0 until 4) {
						if (!okDir(yv, xv, d, u)) continue
						val y = yv + DY[d]; val x = xv + DX[d]
						if (ay[u] == -1 && !canLockHere(y, x, u)) continue
						newOkDir = newOkDir.setBit(4 * i + d)
						count++
					}
					if (count == 0) return false
					if (count > 1) continue
					val d = (0 until 4).first { d -> newOkDir.hasBit(4 * i + d) }
					require(a[yv + DY[d]][xv + DX[d]] == -1 || a[yv + DY[d]][xv + DX[d]] == -10 - u || a[yv + DY[d]][xv + DX[d]] == -10 - v || a[yv + DY[d]][xv + DX[d]] == u) { a[yv + DY[d]][xv + DX[d]] }
					if (a[yv + DY[d]][xv + DX[d]] == -10 - v) {
//						info { "INTERESTING" }
					}
					if (a[yv + DY[d]][xv + DX[d]] == -1) {
						a[yv + DY[d]][xv + DX[d]] = -10 - u
					}
				}
				if (okDir[yv][xv] != newOkDir) {
					okDir[yv][xv] = newOkDir
					changed = true
				}
			}
		} while (changed)
		return true
	}

	var time = 2
	val queue = IntArray((n + 1) * (n + 1))
	val mark = List(n + 1) { IntArray(n + 1) }
	val dist = List(n + 1) { IntArray(n + 1) }
	val prevD = List(n + 1) { IntArray(n + 1) }
	var yRange = 0..n; var xRange = 0..n

	fun bfs(ySource: Int, xSource: Int, idSource: Int, idDest: Int): Path? {
		time++
		var low = 0
		var high = 1
		mark[ySource][xSource] = time
		dist[ySource][xSource] = 0
		prevD[ySource][xSource] = -1
		queue[0] = encode(ySource, xSource)
		val path = mutableListOf<Int>()
		while (low < high) {
			val cellV = queue[low++]
			val (yv, xv) = decode(cellV)
			for (d in 0 until 4) {
				if (low == 1 && !okDir[yv][xv].hasBit(4 * pointNei[idSource].indexOf(idDest) + d)) continue
//				if (low == 1 && !okDir(yv, xv, d, idDest)) continue
				val yu = yv + DY[d]; val xu = xv + DX[d]
//				if (yu !in 0..n || xu !in 0..n) continue // TODO remove
				val au = a[yu][xu]
				val isDest = au == idDest
				if (!isDest && au != -1 && au != -10 - idSource && au != -10 - idDest) continue
				if (isDest && !okDir[yu][xu].hasBit(4 * pointNei[idDest].indexOf(idSource) + d xor 2)) continue
//				if (isDest && !okDir(yu, xu, d xor 2, idSource)) continue
				if (line[encodeLine(yv, xv, d)] || mark[yu][xu] == time) continue
				if (yu !in yRange || xu !in xRange) continue
				mark[yu][xu] = time
				dist[yu][xu] = dist[yv][xv] + 1
				prevD[yu][xu] = d xor 2
				queue[high++] = encode(yu, xu)
				if (isDest) {
					var yy = yu; var xx = xu
					var dFirst = d
					while (true) {
						val dd = prevD[yy][xx]
						if (dd == -1) return Path(path, dFirst, d, idSource, idDest)
						dFirst = dd xor 2
						path.add(encodeLine(yy, xx, dd))
						yy += DY[dd]; xx += DX[dd]
					}
				}
			}
		}
		return null
	}

	val rememberedPaths = List(n + 1) { List(n + 1) { mutableListOf<Path>() } }
	for (v in order) {
		if (v == pointInit) {
			addPoint(v, n / 2, n / 2)
			continue
		}
		fun present(u: Int) = u != -1 && ay[u] != -1
		val us = pointNei[v].filter { present(it) }.toMutableList()
		require(us.isNotEmpty())
		val (yRange_, xRange_) = listOf(ay, ax)
			.map { az -> us.map { az[it] }.let { it.min() - pSearchSpace .. it.max() + pSearchSpace } }
		yRange = yRange_; xRange = xRange_

		val debugScore = List(n + 1) { IntArray(n + 1) { Int.MIN_VALUE } } // TESTING
		fun scorePosition(yv: Int, xv: Int): Double {
			if (a[yv][xv] != -1 && a[yv][xv] != -10 - v) return -inf.toDouble()
			val pcSumLengths = 1000
			val pcSumLengthsSquared = 20
			val pcCos = 10
			val pcSumToSecondNeighbours = 20
			fun cos(y1: Int, x1: Int, y2: Int, x2: Int) =
				(y1 * y2 + x1 * x2) / hypot(y1.toDouble(), x1.toDouble()) / hypot(y2.toDouble(), x2.toDouble())
			var score = 0.0
				//-abs(yv - ay[uFirst]) - abs(xv - ax[uFirst])
			rememberedPaths[yv][xv].clear()
			var failed = false
			addPoint(v, yv, xv)
			calcOkDirs().also { if (!it) failed = true }
			us.sortBy { abs(ay[it] - yv) + abs(ax[it] - xv) }
			for (u in us) {
				if (failed) break
				val bfsResult = bfs(yv, xv, v, u)
				if (bfsResult == null) {
					failed = true
					break
				}
				val bfsPath = bfsResult.path
				addPath(bfsResult)
				rememberedPaths[yv][xv].add(bfsResult)
				calcOkDirs().also { if (!it) failed = true }
				if (failed) break
				score += - pcSumLengths * bfsPath.size - pcSumLengthsSquared * bfsPath.size * bfsPath.size
//				score -= 10000 * maxOf(3 - bfsPath.size, 0)
				score += pcCos * cos(yv - ay[u], xv - ax[u], point6Y[v] - point6Y[u], point6X[v] - point6X[u])
				require(!score.isNaN())
			}
			for (r in rememberedPaths[yv][xv]) removePath(r)
			removePoint(v)
			calcOkDirs().also {
				require(it)
			}
			if (failed) return -inf.toDouble() * 2
			debugScore[yv][xv] = score.roundToInt() // TESTING
			return score
		}
		val (yv, xv) = yRange.cartesianProduct(xRange).maxBy { (yv, xv) -> scorePosition(yv, xv) }
//		log { hex(v) }
//		log { List(n + 1) { y -> List(n + 1) { x -> debugScore[y][x] }.drop(16).dropLast(16) }.filter { row -> row.any { it != 0 } }.joinToString("\n") { it.joinToString("\t") } }
		if (a[yv][xv] != -1 && a[yv][xv] != -10 - v) {
			info { "FAILED_${hex(v).trim()}" }
//			scorePosition(16, 26) // TESTING
			break
		}
		addPoint(v, yv, xv)
		for (r in rememberedPaths[yv][xv]) addPath(r)
		calcOkDirs()
//		log { showTable { p -> debugScore[p.first][p.second].takeIf { it != Int.MIN_VALUE }?.toString() ?: "" } }
//		log { "\n" + showTable { show(a[it.first][it.second], true).padEnd(13, ' ') } }
	}
	log { showTable { show(a[it.first][it.second], false).padEnd(2, ' ') } }
	log { showTable { show(a[it.first][it.second], true).padEnd(13, ' ') } }

	try {
		checkTimeLimit()
	} catch (_: TimeOutException) {
	}
	log?.close()
	return f.map { it.clone() }.also { it[7][0] = 0 }
}

private fun List<String>.joinToStringTrimmed(): String {
	val lines = this.filter { line -> line.any { it != ' ' } }
	val toTrim = lines.minOfOrNull { line -> line.indexOfFirst { it != ' ' } }
		?: return ""
	return lines.joinToString("\n") { it.substring(toTrim).trimEnd() }
}

val DY = intArrayOf(0, 1, 0, -1)
val DX = intArrayOf(1, 0, -1, 0)

private fun <T, R> Iterable<T>.cartesianProduct(other: Iterable<R>) = flatMap { x -> other.map { y -> x to y } }
private fun <T> Iterable<T>.cartesianSquare() = flatMap { x -> map { y -> x to y } }
private fun encode(y: Int, x: Int) = (y shl 16) or x
private fun decode(code: Int) = (code shr 16) to (code and 0xFFFF)
private fun Int.bit(index: Int) = ushr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
private fun Int.setBit(index: Int) = or(1 shl index)

class TimeOutException : RuntimeException()

fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	timeStart = System.currentTimeMillis()
	fun readLn() = `in`.readLine()!!
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()

	val (n, m) = readInts()
	val f = List(n) { readInts().toIntArray() }
	out.println(solve(f, m, toVisualize).joinToString("\n") { it.joinToString(" ") })
	out.close()
	return toVisualize
}

private inline fun log(msg: () -> Any) { log?.println(msg()) }
private inline fun info(msg: () -> Any) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
