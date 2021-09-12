package marathons.atcoder.nihonbashi2021

import java.io.BufferedReader
import java.io.BufferedWriter
import java.util.concurrent.Callable
import kotlin.math.abs
import kotlin.random.Random

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(100, 1, false, Visualizer(::solve)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
const val TIME_LIMIT = 1500L
val random = Random(566)
var timeStart = 0L
fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT

fun solveOnce(n: Int, days: Int, vegetables: List<List<Int>>): Pair<Long, MutableList<List<Int>>> {
	if (timePassed() > 1) return 0L to mutableListOf()
	val pMaxMachines = n * r(50, 1) / 16
	val pBfsPathLength = n * r(6, 2) / 8
	val pSingleMachineDays = r(24, 2)
	val pPreferLongPaths = r(1, 1) / 4.0
	val pFutureScore = r(6, 3) * n
	val pCenter1 = r(1, 1)
	val pCenter2 = r(1, 1)
	val pFutureImportance = r(1, 1)
	val machines = mutableSetOf<Pair<Int, Int>>()
	val field = List(n) { BooleanArray(n) }
	val vegId = List(days) { List(n) { IntArray(n) { -1 } } }
	val cumulative = List(days + 1) { List(n) { IntArray(n) { -1 } } }
	val vegValue = IntArray(vegetables.size)
	vegetables.forEachIndexed { index, (x, y, start, end, value) ->
		cumulative[start + 1][x][y] = value
		for (d in start..end) {
			vegId[d][x][y] = index
		}
		vegValue[index] = value
	}
	for (d in vegId.indices) for (x in field.indices) for (y in field.indices) {
		cumulative[d + 1][x][y] += cumulative[d][x][y]
	}
	val harvested = BooleanArray(vegetables.size)
	var money = 1L
	var day = 0
	val answer = mutableListOf<List<Int>>()

	fun price() = (machines.size + 1).let { it.toLong() * it * it }
	fun finishDay(action: List<Int>) {
		answer.add(action)
		for ((x, y) in machines) {
			val id = vegId[day][x][y].takeIf { it >= 0 } ?: continue
			if (!harvested[id]) {
				harvested[id] = true
				money += vegValue[id] * machines.size
			}
		}
		day++
	}
	fun doPass() = finishDay(listOf(-1))
	fun doPurchase(x: Int, y: Int) {
		money -= price()
		field[x][y] = true
		machines.add(x to y)
		finishDay(listOf(x, y))
	}
	fun doMove(xFrom: Int, yFrom: Int, xTo: Int, yTo: Int) {
		field[xFrom][yFrom] = false
		field[xTo][yTo] = true
		machines.remove(xFrom to yFrom)
		machines.add(xTo to yTo)
		finishDay(listOf(xFrom, yFrom, xTo, yTo))
	}

	val mark = List(n) { IntArray(n) }
	val tIn = List(n) { IntArray(n) }
	val fUp = List(n) { IntArray(n) }
	var dfsTime = 0
	var markTime = 0
	val crowdNei = mutableSetOf(0 to 0)
	var crowdLastBestFuture = 0
	var crowdLast = 0 to 0
	fun futureScore(x: Int, y: Int): Int {
		val upTo = minOf(days, day + machines.size * (days + pFutureImportance * day) / days)
		return cumulative[upTo][x][y] - cumulative[day][x][y]
	}
	fun dfsCrowd(x: Int, y: Int, dHow: Int) {
		mark[x][y] = markTime
		tIn[x][y] = dfsTime
		fUp[x][y] = dfsTime++
		var isArticulation = false
		for (d in DX.indices) {
			if (d == dHow xor 2) continue
			val (xx, yy) = x + DX[d] to y + DY[d]
			if (xx < 0 || xx >= n || yy < 0 || yy >= n) continue
			if (mark[xx][yy] == markTime) {
				fUp[x][y] = minOf(fUp[x][y], tIn[xx][yy])
				continue
			}
			if (!field[xx][yy]) {
				crowdNei.add(xx to yy)
				continue
			}
			dfsCrowd(xx, yy, d)
			fUp[x][y] = minOf(fUp[x][y], fUp[xx][yy])
			if (fUp[xx][yy] >= tIn[x][y]) isArticulation = true
		}
		if (!isArticulation) {
			val fs = futureScore(x, y)
			if (fs < crowdLastBestFuture) {
				crowdLastBestFuture = fs
				crowdLast = x to y
			}
		}
	}
	fun dfsCrowd() {
		markTime++
		crowdNei.clear()
		val (sx, sy) = machines.first()
		dfsCrowd(sx, sy, -1)
	}
	fun dfsCrowdStrange(x: Int, y: Int) {
		markTime++
		crowdLastBestFuture = Int.MAX_VALUE
		dfsCrowd(x, y, -1)
	}

	val dist = List(n) { IntArray(n) }
	val prev = List(n) { IntArray(n) }
	val score = List(n) { LongArray(n) }
	fun nonCenterPenalty(x: Int, y: Int) = abs(2 * x - n) + abs(2 * y - n)
	fun harvestScore(x: Int, y: Int, future: Int): Long {
		val id = vegId[day + future - 1][x][y]
		val harvestScore = if (id == -1 || harvested[id]) 0 else vegValue[id].toLong()
		return harvestScore * pFutureScore + futureScore(x, y)
	}
	fun bfsPath(): Pair<Int, Int> {
		markTime++
		var bestXY = -1 to 0
		val maxLength = minOf(pBfsPathLength, days - day)
		if (maxLength == 0) return bestXY
		val queue = mutableListOf<Pair<Int, Int>>()
		var bestUnitScore = 0.0
		fun setScore(x: Int, y: Int, newScore: Long) {
			score[x][y] = newScore
			val unitScore = newScore.toDouble() / (dist[x][y] + pPreferLongPaths)
			if (unitScore > bestUnitScore) {
				bestUnitScore = unitScore
				bestXY = x to y
			}
		}
		for ((x, y) in crowdNei) {
			queue.add(x to y)
			mark[x][y] = markTime
			dist[x][y] = 1
			setScore(x, y, harvestScore(x, y, 1))
		}
		var low = 0
		while (low < queue.size) {
			val (x, y) = queue[low++]
			val distXY = dist[x][y]
			if (distXY == maxLength) break
 			for (d in DX.indices) {
				val (xx, yy) = x + DX[d] to y + DY[d]
				if (xx < 0 || xx >= n || yy < 0 || yy >= n || field[xx][yy]) continue
				val unmarked = mark[xx][yy] < markTime
				if (unmarked || dist[xx][yy] == distXY + 1) {
					dist[xx][yy] = distXY + 1
					val newScore = score[x][y] + harvestScore(xx, yy, distXY + 1)
					if (unmarked || newScore > score[xx][yy]) {
						setScore(xx, yy, newScore)
						prev[xx][yy] = d
					}
				}
				if (unmarked) {
					mark[xx][yy] = markTime
					queue.add(xx to yy)
				}
			}
		}
//		print("$bestUnitScore ")
		return bestXY
	}

	while (day < days) {
		if (timePassed() > 1) return 0L to mutableListOf()
		if (machines.isEmpty()) {
			val (sx, sy) = vegetables.minByOrNull { it[2] * n + nonCenterPenalty(it[0], it[1]) }!!.take(2)
			doPurchase(sx, sy)
			continue
		}
		dfsCrowd()
		fun canBuy() = price() <= money && machines.size < pMaxMachines && day > pSingleMachineDays
		if (canBuy()) {
			val (sx, sy) = crowdNei.minByOrNull { (x, y) -> -harvestScore(x, y, 1) * 2 * n + nonCenterPenalty(x, y) * pCenter1 }!!
			doPurchase(sx, sy)
			continue
		}
		if (machines.size == 1) {
			val (sx, sy) = field.indices.cartesianSquare().minByOrNull { (x, y) -> -harvestScore(x, y, 1) * 2 * n + nonCenterPenalty(x, y) * pCenter2 }!!
			val (ox, oy) = machines.first()
			doMove(ox, oy, sx, sy)
			continue
		}
		val (bx, by) = bfsPath()
		if (bx >= 0) {
			val path = mutableListOf(bx to by)
			var (tx, ty) = bx to by
			while (dist[tx][ty] > 1) {
				val d = prev[tx][ty]
				tx -= DX[d]; ty -= DY[d]
				path.add(tx to ty)
			}
			path.reverse()
//			println("$day $path")
			for (i in path.indices) {
				val (nx, ny) = path[i]
				if (canBuy()) {
					doPurchase(nx, ny)
					continue
				}
				dfsCrowdStrange(nx, ny)
				val (ox, oy) = crowdLast
				doMove(ox, oy, nx, ny)
			}
			continue
		}
		doPass()
	}
	if (!SUBMIT) print("[$money]\t")
	return money to answer
}

fun solve(n: Int, days: Int, vegetables: List<List<Int>>): MutableList<List<Int>> {
	val tries =
		List(16) {
//		generateSequence {
			solveOnce(n, days, vegetables).takeIf { it.first > 0 }
		}.toList().filterNotNull()
	return tries.minByOrNull { -it.first }!!.second
}

fun solve(`in`: BufferedReader, out: BufferedWriter) {
	timeStart = System.currentTimeMillis()
	fun readLn() = `in`.readLine()!!
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }

	val (n, m, t) = readInts()
	val vegetables = List(m) { readInts() }
	out.write(solve(n, t, vegetables).joinToString("\n") { it.joinToString(" ") })
	out.close()
}

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)
fun <T> Iterable<T>.cartesianSquare() = flatMap { x -> map { y -> x to y } }

fun r(median: Int, std: Int) = median - std + random.nextInt(2 * std + 1)

fun main() {
	@Suppress("UNNECESSARY_SAFE_CALL")
	EVALUATOR?.call() ?: solve(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
