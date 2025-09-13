package marathons.atcoder.ahc53_asprova12_randomSumGame //TESTING

import java.io.*
import java.util.*
import kotlin.math.roundToLong
import kotlin.random.Random

private val TO_EVAL = (0 until 100)
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null

const val n = 500
const val m = 50
const val f = 3 * m
const val low = 998000000000000L
const val up = 1002000000000000L

private fun solveMakeArray(): LongArray {
	val a = LongArray(n) { low }
	val random = Random(566)
	val samples = List(20) {
		val s = LongArray(m) { random.nextLong(low, up + 1) }
		s.sort()
		s
	}
	for (i in 0 until m) {
		val si = samples.minOf { s -> s[i] }
		a[2 * i] = (si * (0.5001)).roundToLong()
		a[2 * i + 1] = (si * (0.4998)).roundToLong()
		a[2 * m + i] = ((si * 0.001) * (i + 1) / m).roundToLong()//(2e10 * (i + 1)).roundToLong()
	}
	var t = 1.0
	for (i in m - 10 until m) {
		t *= 1.1
		a[2 * m + i] = (a[2 * m + i] * t).roundToLong()
	}
	for (i in 0 until 2) a[i] = low / 2
	Arrays.sort(a, 0, 2 * m)
	val above = mutableListOf<Long>()
	for (s in samples) for (i in 0 until m) above.add(s[i] - a[i])
	var x = 0L//above.average().roundToLong()
	for (i in f until n) {
		if (i == f) x = (1e11).roundToLong()
		if (i == f + m) x = (1e9).roundToLong()
		a[i] = x
		x = (x * (1 - 0.58 / m)).roundToLong()
		if (i < f + m) x = (x * (1 - 2.0 / m)).roundToLong()
		if (i > f + 3 * m && i % m == m / 2) x = (x * 0.38).roundToLong()
	}
	return a
}
val a = solveMakeArray()

private fun solve(b: LongArray): IntArray {
	val ans = IntArray(n) { -1 }
	val treeSet = TreeSet(a.toList().subList(2 * m, 3 * m))
	treeSet.add(0)
	for (i in 0 until m) {
		var best = Triple(Long.MAX_VALUE, -1, -1)
		for (x in 0 until 2 * m) {
			if (ans[x] != -1) continue
			for (y in 0 until x) {
				if (ans[y] != -1) continue
				if (a[x] + a[y] > b[i]) continue
				val forZ = b[i] - a[x] - a[y]
				val z = treeSet.floor(forZ)!!
				val score = forZ - z
				if (score < best.first) best = Triple(score, x, y)
			}
		}
		if (best.second == -1) {
			info { "OP" }
			val x = a.indices.first { x -> ans[x] == -1 }
			ans[x] = i
			b[i] -= a[x]
			val y = a.indices.first { y -> ans[y] == -1 }
			ans[y] = i
			b[i] -= a[y]
			continue
		}
		ans[best.second] = i
		ans[best.third] = i
		val forZ = b[i] - a[best.second] - a[best.third]
		b[i] = forZ
		val z = treeSet.floor(forZ)!!
		if (z > 0) {
			treeSet.remove(z)
			ans[a.indexOf(z)] = i
			b[i] -= z
		}
	}

	while (true) {
		var best = Triple(Long.MAX_VALUE, -1, -1)
		for (i in 0 until m) for (j in f until n) {
			if (ans[j] != -1) continue
			if (a[j] > b[i]) continue
			if (b[i] - a[j] < best.first) best = Triple(b[i] - a[j], i, j)
		}
		val (_, i, j) = best
		if (i == -1) break
		ans[j] = i
		b[i] -= a[j]
	}

	while (true) {
		var best = Triple(-1L, -1, -1)
		for (i in 0 until m) for (j in f until n) {
			if (ans[j] != -1 || b[i] <= 0) continue
			require(a[j] > b[i])
			if (a[j] >= 2 * b[i]) continue
			if (2 * b[i] - a[j] > best.first) best = Triple(2 * b[i] - a[j], i, j)
		}
		val (_, i, j) = best
		if (i == -1) break
		ans[j] = i
		b[i] -= a[j]
	}

	return ans
}

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }
fun BufferedReader.readLongs() = readStrings().map { it.toLong() }

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	`in`.readln()
	out.println(a.joinToString(" "))
	out.flush()
	val b = `in`.readLongs().toLongArray()
	val ans = solve(b)
	out.println(ans.map { it + 1 }.joinToString(" "))
	out.flush()
	out.close()
	log?.close()
	return null
}

private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
