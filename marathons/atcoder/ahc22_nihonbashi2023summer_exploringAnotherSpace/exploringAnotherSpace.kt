package marathons.atcoder.ahc22_nihonbashi2023summer_exploringAnotherSpace //TESTING

import java.io.*
import java.util.*
import java.util.concurrent.Callable
import javax.annotation.processing.Generated
import kotlin.math.*
import kotlin.random.Random
import kotlin.random.asJavaRandom

private val EVALUATOR: Callable<Void?>
//		= ExploringAnotherSpaceEval() //TESTING
		= marathons.utils.Evaluator(marathons.utils.atcoder.atcoderVisualizer(::solveIO, true)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
const val MAX_TEMP = 1_000
const val MAX_MEASUREMENTS = 10_000

fun solve(judge: Judge): List<Any>? {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	strategyVV(judge)
	log?.close()
	return null
}

fun strategyVV(judge: Judge) {
	val random = Random(566).asJavaRandom()
	val (m, noise, wormholes) = judge.getParameters()
	debug { "M=$m\tNoise=$noise\tW=${wormholes.size}\t" }

	fun shiftMin(dz: Int) = (dz mod m).let { listOf(it, it - m) }.minBy { abs(it) }

//	val rr = Random(System.currentTimeMillis())
//	val p1 = rr.nextInt(21)
//	val p2 = rr.nextInt(21)
//	val level1 = p1 / 20.0
//	val level2 = p2 / 20.0

//	val level1 = (0.1 + noise * 2.0 / MAX_TEMP).coerceIn(0.0, 1.0)
//	val level2 = (0.2 + noise * 2.0 / MAX_TEMP).coerceIn(0.0, 1.0)

	val case = (((noise * 1.0).roundToInt())).toString() + "_" + (m - 10) / 14
	val (p1, p2) = generated(case).toList().map { it / 10.0 }
	val pUncofdifence = 1
	val pStripe = 3
	val pStripeGradient = 2

	val confidence = ln(wormholes.size.toDouble()) - pUncofdifence
	val level1 = p1 / 10.0
	val level2 = p2 / 10.0

	val temperature = List(m) { i -> IntArray(m) { j ->
		fun f(x: Double): Double {
			if (x < 0.25) return 0.0
			if (x < 0.5) return (x - 0.25) * 4
			if (x < 0.75) return 1.0
			return (1 - x) * 4
		}
		val y = i * 1.0 / m
		val x = j * 1.0 / m
		(((2 * f(y) + f(x) - 1.5) / 3 * level1 + 0.5) * MAX_TEMP).roundToInt()
	} }
	run {
		val k = (temperature.indices.firstOrNull { temperature[it][it] > MAX_TEMP / 2 } ?: (3 * m / 8)) - 1
		val special = mutableSetOf(k to k)
		fun t(level: Double) = ((0.5 + (level - 0.5) * level2) * MAX_TEMP).roundToInt()
		fun setSpecial(y: Int, x: Int, level: Double) {
			temperature[y][x] = t(level)
			special.add(y to x)
		}
		setSpecial(k, k, 0.0)
		for (i in 0 until pStripe) {
			setSpecial(k + 1 + i, k, 1.0)
			setSpecial(k, k + 1 + i, 1.0)
		}
		setSpecial(k + 1, k + 1, 1.0)
		val x1 = m / 4
		val x2 = maxOf(m / 2, k + pStripe + pStripeGradient)
		for (i in x1..x2) {
			special.addAll(listOf(x1 to i, x2 to i, i to x1, i to x2))
		}
		val atDiagonal = 0.7
		setSpecial(k + 1, k - 1, atDiagonal)
		setSpecial(k, k - 1, atDiagonal / 2)
		setSpecial(k - 1, k, 1.0 / 3)
		setSpecial(k - 1, k + 1, 2.0 / 3)
		for (y in x1..x2) for (x in x1..x2) {
			if ((y to x) in special) continue
			run {
				val closest = special
					.map { (ys, xs) -> Triple(ys, xs, abs(ys - y) + abs(xs - x)) }
					.sortedBy { it.third }
				val (y0, x0) = closest[0].first to closest[0].second
				val j = (1 until closest.size).first { j ->
					val (yj, xj) = closest[j].first to closest[j].second
					abs(yj - y) + abs(xj - x) != abs(yj - y0) + abs(xj - x0) + abs(y0 - y) + abs(x0 - x)
				}
				val t1 = temperature[y0][x0]; val d1 = closest[0].third
				val t2 = temperature[closest[j].first][closest[j].second]; val d2 = closest[j].third
				temperature[y][x] = ((t1 * d2 + t2 * d1).toDouble() / (d1 + d2)).roundToInt()
			}
			/*run {
				val d1 = abs(y - k) + abs(x - k)
				val d2 = minOf(
					abs(y - k) + abs(x - x.coerceIn(k + 1, k + stripe)),
					abs(y - y.coerceIn(k + 1, k + stripe)) + abs(x - k),
				)
				temperature[y][x] = ((0 * d2 + MAX_TEMP * d1).toDouble() / (d1 + d2)).roundToInt()
			}*/
		}
		while (true) {
			var improve = false
			for (y in x1..x2) for (x in x1..x2) {
				if ((y to x) in special) continue
				fun penalty(t: Int) = (0 until 4).sumOf { d -> sqr(temperature[y + DY[d]][x + DX[d]] - t) }
				val p0 = penalty(temperature[y][x])
				for (delta in -1..1 step 2) {
					if (penalty(temperature[y][x] + delta) < p0) {
						temperature[y][x] += delta
						improve = true
						break
					}
				}
			}
			if (!improve) break
		}
	}
	log?.println(temperature.joinToString("\n") { it.contentToString() })

	val bestShiftCheckMemo = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
	fun bestShiftCheck(w1: Int, w2: Int): List<Int> {
		val (yw1, xw1) = wormholes[w1]; val (yw2, xw2) = wormholes[w2]
		val dy = shiftMin(yw2 - yw1); val dx = shiftMin(xw2 - xw1)
//		if (maxOf(abs(dy), abs(dx)) > m / 4) return null
		if (bestShiftCheckMemo[dy to dx] == null) {
			var bestDiff = -1
			var bestShift = 0 to 0
			for (sy in 0 until m) for (sx in 0 until m) {
				val ny = (dy + sy) mod m; val nx = (dx + sx) mod m
				val diff = abs(temperature[sy][sx] - temperature[ny][nx])
				if (diff > bestDiff) {
					bestDiff = diff
					bestShift = sy to sx
				}
			}
			bestShiftCheckMemo[dy to dx] = bestShift
		}
		val (sy, sx) = bestShiftCheckMemo[dy to dx]!!
		return listOf(shiftMin(sy - yw1), shiftMin(sx - xw1))
	}

	judge.putTemperature(temperature)
	var scoreTemp = 0L
	for (y in temperature.indices) for (x in temperature.indices) {
		scoreTemp += sqr(temperature[y][x] - temperature[(y + 1) % m][x])
		scoreTemp += sqr(temperature[y][x] - temperature[y][(x + 1) % m])
	}

	val measurements = List(wormholes.size) { mutableListOf<Triple<Int, Int, Int>>() }
	var measurementCount = 0
	var scoreMeasure = 0L
	val e = List(wormholes.size) { DoubleArray(wormholes.size) { 0.0 } }
	fun probGaussianAtMost(truth: Int, value: Int): Double {
		if (value < 0) return 0.0
		if (value >= MAX_TEMP) return 1.0
		// truth + noise * rnd < value + 0.5
		// rnd < (value + 0.5 - truth) / noise
		return phi((value + 0.5 - truth) / noise)
	}
	fun probGaussianExactly(truth: Int, value: Int): Double {
		return probGaussianAtMost(truth, value) - probGaussianAtMost(truth, value - 1)
	}
	fun measure(id: Int, dy: Int, dx: Int) {
		measurementCount++
		scoreMeasure += 100 * (10 + abs(dy) + abs(dx))
		val measurement = judge.getNoisyTemperature(id, dy, dx)
		measurements[id].add(Triple(dy, dx, measurement))
		for (j in wormholes.indices) {
			val y = (wormholes[j].first + dy) mod m
			val x = (wormholes[j].second + dx) mod m
			val truth = temperature[y][x]
			val p = probGaussianExactly(truth, measurement)
			e[id][j] +=	(-ln(p)).coerceAtMost(1e9)
		}
	}


	for (i in wormholes.indices) measure(i, 0, 0)
	var assignment = IntArray(wormholes.size) { it }
	improvementLoop@for (day in 0 until MAX_MEASUREMENTS) {
		var satisfied = true
		assignment = hungarian(e)
		for (i in wormholes.indices.shuffled(random)) {
			if (measurementCount == MAX_MEASUREMENTS) break@improvementLoop
			val (jBest, eBest) = e[i].withIndex().minBy { it.value }
			val (jSecond, eSecond) = e[i].withIndex().filter { it.index != jBest }.minBy { it.value }
			if (eSecond > eBest + confidence && jBest == assignment[i]) continue
			satisfied = false
			val yx = bestShiftCheck(jBest, jSecond)
//			val jump = m / 4; measure(i, jump * DX[d], jump * DY[d])
//			List(2) { shiftMin((jump * random.nextGaussian()).roundToInt()) }
			measure(i, yx[0], yx[1])
		}
		if (satisfied) break
	}
//	debug { "MES=${if (measurementCount == MAX_MEASUREMENTS) "999" else (measurementCount * 1.0 / wormholes.size).roundToInt()}\t" }
	debug { "p1=$p1\tp2=$p2\t" }
	debug { "\$T=$scoreTemp\t\$M=$scoreMeasure\t" }

	log?.println(assignment.contentToString())
	if (VERBOSE) for (i in wormholes.indices) {
		val j = assignment[i]
		log?.println("$i:\tassign $j\twith ${e[i][j]}\tVS ${e[i].filterIndexed { index, _ -> index != j }.min()}")
	}

	judge.putAnswer(assignment)
}

interface Judge {
	fun getParameters(): Triple<Int, Int, List<Pair<Int, Int>>>
	fun putTemperature(temperature: List<IntArray>)
	fun getNoisyTemperature(id: Int, dy: Int, dx: Int): Int
	fun putAnswer(answer: IntArray)
}

class IOJudge(val `in`: BufferedReader, val out: PrintWriter) : Judge {
	fun readln() = `in`.readLine()!!
	fun readInt() = readln().toInt()
	fun readStrings() = readln().split(" ")
	fun readInts() = readStrings().map { it.toInt() }

	override fun getParameters(): Triple<Int, Int, List<Pair<Int, Int>>> {
		val (m, w, noise) = readInts()
		val wormholes = List(w) { readInts().toPair() }
		return Triple(m, noise, wormholes)
	}

	override fun putTemperature(temperature: List<IntArray>) {
		for (row in temperature) out.println(row.joinToString(" "))
	}

	override fun getNoisyTemperature(id: Int, dy: Int, dx: Int): Int {
		out.println("$id $dy $dx")
		return readInt()
	}

	override fun putAnswer(answer: IntArray) {
		out.println("-1 -1 -1")
		out.println(answer.joinToString("\n"))
		out.close()
	}
}

fun sqr(x: Int) = x * x

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

fun erf(z: Double): Double {
	val t = 1.0 / (1.0 + 0.47047 * abs(z))
	val poly = t * (0.3480242 + t * (-0.0958798 + t * 0.7478556))
	val ans = 1.0 - poly * exp(-z * z)
	return if (z >= 0) ans else -ans
}
fun phi(x: Double): Double {
	return 0.5 * (1 + erf(x / sqrt(2.0)))
}

fun hungarian(matrix: List<DoubleArray>): IntArray {
	var e = matrix
	val ans = IntArray(e.size)
	Arrays.fill(ans, -1)
	if (e.isEmpty() || e[0].isEmpty()) return ans
	val infty = 1e50
	var swap = false
	if (e.size > e[0].size) {
		swap = true
		val f = List(e[0].size) { DoubleArray(e.size) }
		for (i in e.indices) for (j in e[0].indices) f[j][i] = e[i][j]
		e = f
	}
	val n1 = e.size
	val n2 = e[0].size
	val u = DoubleArray(n1 + 1)
	val v = DoubleArray(n2 + 1)
	val p = IntArray(n2 + 1)
	val way = IntArray(n2 + 1)
	for (i in 1..n1) {
		p[0] = i
		var j0 = 0
		val minV = DoubleArray(n2 + 1)
		Arrays.fill(minV, infty)
		val used = BooleanArray(n2 + 1)
		do {
			used[j0] = true
			val i0 = p[j0]
			var j1 = 0
			var delta = infty
			for (j in 1..n2) {
				if (!used[j]) {
					val cur = e[i0 - 1][j - 1] - u[i0] - v[j]
					if (cur < minV[j]) {
						minV[j] = cur
						way[j] = j0
					}
					if (minV[j] < delta) {
						delta = minV[j]
						j1 = j
					}
				}
			}
			for (j in 0..n2) {
				if (used[j]) {
					u[p[j]] += delta
					v[j] -= delta
				} else {
					minV[j] -= delta
				}
			}
			j0 = j1
		} while (p[j0] != 0)
		do {
			val j1 = way[j0]
			p[j0] = p[j1]
			j0 = j1
		} while (j0 > 0)
	}
	for (j in 1..n2) {
		if (p[j] > 0) {
			// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
			// sum += e[p[j] - 1][j - 1];
			if (swap) {
				ans[j - 1] = p[j] - 1
			} else {
				ans[p[j] - 1] = j - 1
			}
		}
	}
	return ans
}

private inline fun debug(msg: () -> Any) { if (VERBOSE) print(msg()) }

fun solveIO(`in`: BufferedReader, out: PrintWriter) = solve(IOJudge(`in`, out))

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out, true))
}

private fun <T> List<T>.toPair() = get(0) to get(1)

//// KOTLIN 1.3 ////
infix fun Int.mod(other: Int): Int {
	val r = this % other
	return r + (other and (((r xor other) and (r or -r)) shr 31))
}
inline fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
	val iterator = iterator()
	if (!iterator.hasNext()) throw NoSuchElementException()
	var minElem = iterator.next()
	if (!iterator.hasNext()) return minElem
	var minValue = selector(minElem)
	do {
		val e = iterator.next()
		val v = selector(e)
		if (minValue > v) {
			minElem = e
			minValue = v
		}
	} while (iterator.hasNext())
	return minElem
}
fun Iterable<Double>.minOrNull(): Double? {
	val iterator = iterator()
	if (!iterator.hasNext()) return null
	var min = iterator.next()
	while (iterator.hasNext()) {
		val e = iterator.next()
		min = minOf(min, e)
	}
	return min
}
inline fun <T> Iterable<T>.sumOf(selector: (T) -> Int): Int {
	var sum = 0
	for (element in this) {
		sum += selector(element)
	}
	return sum
}

@Generated //// GENERATED ////
fun generated(case: String) = when (case) {
	"100_0" -> 3 to 4
	"100_1" -> 5 to 3
	"100_2" -> 4 to 5
	"121_0" -> 3 to 4
	"121_1" -> 4 to 5
	"121_2" -> 4 to 5
	"144_0" -> 4 to 4
	"144_1" -> 4 to 4
	"144_2" -> 5 to 5
	"169_0" -> 4 to 5
	"169_1" -> 5 to 4
	"169_2" -> 6 to 4
	"16_0" -> 2 to 0
	"16_1" -> 2 to 1
	"16_2" -> 2 to 2
	"196_0" -> 4 to 6
	"196_1" -> 5 to 5
	"196_2" -> 5 to 7
	"1_0" -> 1 to 0
	"1_1" -> 1 to 0
	"1_2" -> 1 to 0
	"225_0" -> 5 to 5
	"225_1" -> 6 to 7
	"225_2" -> 6 to 7
	"256_0" -> 6 to 5
	"256_1" -> 5 to 6
	"256_2" -> 7 to 8
	"25_0" -> 2 to 2
	"25_1" -> 2 to 2
	"25_2" -> 3 to 1
	"289_0" -> 6 to 6
	"289_1" -> 6 to 7
	"289_2" -> 8 to 8
	"324_0" -> 6 to 7
	"324_1" -> 6 to 7
	"324_2" -> 7 to 7
	"361_0" -> 6 to 6
	"361_1" -> 7 to 6
	"361_2" -> 6 to 9
	"36_0" -> 2 to 2
	"36_1" -> 3 to 2
	"36_2" -> 3 to 3
	"400_0" -> 5 to 7
	"400_1" -> 5 to 8
	"400_2" -> 7 to 10
	"441_0" -> 8 to 6
	"441_1" -> 9 to 8
	"441_2" -> 10 to 8
	"484_0" -> 6 to 9
	"484_1" -> 10 to 10
	"484_2" -> 10 to 9
	"49_0" -> 3 to 0
	"49_1" -> 2 to 3
	"49_2" -> 4 to 3
	"4_0" -> 1 to 0
	"4_1" -> 1 to 0
	"4_2" -> 1 to 1
	"529_0" -> 8 to 8
	"529_1" -> 8 to 8
	"529_2" -> 9 to 8
	"576_0" -> 8 to 8
	"576_1" -> 9 to 9
	"576_2" -> 8 to 10
	"625_0" -> 10 to 8
	"625_1" -> 9 to 10
	"625_2" -> 5 to 10
	"64_0" -> 3 to 3
	"64_1" -> 3 to 3
	"64_2" -> 4 to 4
	"676_0" -> 10 to 10
	"676_1" -> 10 to 10
	"676_2" -> 9 to 10
	"729_0" -> 10 to 10
	"729_1" -> 10 to 10
	"729_2" -> 10 to 10
	"784_0" -> 10 to 9
	"784_1" -> 9 to 10
	"784_2" -> 10 to 10
	"81_0" -> 5 to 1
	"81_1" -> 3 to 4
	"81_2" -> 4 to 4
	"841_0" -> 9 to 10
	"841_1" -> 10 to 10
	"841_2" -> 9 to 10
	"900_0" -> 10 to 10
	"900_1" -> 10 to 10
	"900_2" -> 10 to 10
	"9_0" -> 1 to 1
	"9_1" -> 2 to 0
	"9_2" -> 2 to 0
	else -> error("")
}
