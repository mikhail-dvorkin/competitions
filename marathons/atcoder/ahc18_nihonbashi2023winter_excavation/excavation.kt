package marathons.atcoder.ahc18_nihonbashi2023winter_excavation //TESTING

import java.io.*
import java.util.concurrent.Callable
import kotlin.math.abs
import kotlin.random.Random

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(marathons.utils.atcoder.atcoderVisualizerCallable(::solveIO, true)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
class TimeOutException : RuntimeException()

fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	fun readLn() = `in`.readLine()!!
	fun readInt() = readLn().toInt()
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()

	val (n, wellsCount, housesCount, stamina) = readInts()
	data class Object(val y: Int, val x: Int)
	val objects = List(wellsCount + housesCount) { readInts().let { Object(it[0], it[1]) } }
	val (wells, houses) = objects.take(wellsCount) to objects.takeLast(housesCount)

	val crushed = List(n) { BooleanArray(n) }
	val force = stamina + 64
	fun dig(y: Int, x: Int) {
		if (crushed[y][x]) return
		out.println("$y $x $force")
		val result = readInt()
		if (result == 0) return dig(y, x)
		crushed[y][x] = true
		if (result != 1) throw TimeOutException()
	}

	val inf = Int.MAX_VALUE / 2
	fun dist(a: Object, b: Object) = abs(a.y - b.y) + abs(a.x - b.x)
	fun connect(a: Object, b: Object) {
		for (y in a.y towards b.y) dig(y, a.x)
		for (x in a.x towards b.x) dig(b.y, x)
	}

	val mark = List(n) { IntArray(n) }
	var markTime = 0

	fun simpleSteiner(points: List<Object>, actually: Boolean): Int {
		val edges = points.indices.cartesianTriangle().sortedBy { dist(points[it.first], points[it.second]) }
		val dsu = DisjointSetUnion(points.size)
		var result = 0
		for ((i, j) in edges) {
			if (dsu[i] == dsu[j]) continue
			dsu.unite(i, j)
			result += dist(points[i], points[j])
			if (actually) {
				val toConnect = mutableListOf(points[i], points[j])
				var notImproved = 0
				while (notImproved < 2) {
					markTime++
					var best = toConnect[0]
					fun dfs(y: Int, x: Int) {
						mark[y][x] = markTime
						val here = Object(y, x)
						if (dist(here, toConnect[1]) < dist(best, toConnect[1])) best = here
						for (d in 0 until 4) {
							val yy = y + DY[d]; val xx = x + DX[d]
							if (yy !in mark.indices || xx !in mark.indices) continue
							if (!crushed[yy][xx]) continue
							if (mark[yy][xx] == markTime) continue
							dfs(yy, xx)
						}
					}
					if (crushed[best.y][best.x]) dfs(best.y, best.x)
					if (best != toConnect[0]) {
						toConnect[0] = best
					} else {
						notImproved++
					}
					toConnect.reverse()
				}
				val corners = listOf(Object(toConnect[0].y, toConnect[1].x), Object(toConnect[1].y, toConnect[0].x))
				val selected = corners.minBy { corner ->
					points.indices.filter { it != i && it != j }.minOfOrNull { dist(points[it], corner) }
						?: 0
				}
				connect(toConnect[0], selected)
				connect(selected, toConnect[1])
			}
		}
		return result
	}
	fun selectComponents(): List<List<Object>> {
		val housesMasks = 1 shl housesCount
		val a = List(wellsCount + 1) { IntArray(housesMasks) { inf } }
		val how = List(wellsCount + 1) { IntArray(housesMasks) }
		a[0][0] = 0
		for (i in wells.indices) {
			for (mask in 0 until housesMasks) {
				a[i + 1][mask] = a[i][mask]
			}
			for (mask in 1 until housesMasks) {
				val list = houses.filterIndexed { index, _ -> mask.hasBit(index) } + wells[i]
				val cost = simpleSteiner(list, false)
				for (other in 0 until housesMasks) {
					if ((other and mask) != 0) continue
					if (a[i][other] == inf) continue
					if (a[i + 1][other + mask] > a[i][other] + cost) {
						a[i + 1][other + mask] = a[i][other] + cost
						how[i + 1][other + mask] = mask
					}
				}
			}
		}
		var toCover = housesMasks - 1
		val result = mutableListOf<List<Object>>()
		for (i in wells.indices.reversed()) {
			if (toCover == 0) break
			val mask = how[i + 1][toCover]
			if (mask > 0) {
				val list = houses.filterIndexed { index, _ -> mask.hasBit(index) } + wells[i]
				result.add(list)
			}
			toCover -= mask
		}
		return result
	}
	try {
		val components = selectComponents()
		for (component in components) {
			simpleSteiner(component, true)
		}
	} catch (_: TimeOutException) {
	}
	return toVisualize
}

class DisjointSetUnion(n: Int) {
	var p: IntArray
	var r: Random = Random(566)

	init {
		p = IntArray(n)
		clear()
	}

	fun clear() {
		for (i in p.indices) {
			p[i] = i
		}
	}

	operator fun get(v: Int): Int {
		if (p[v] == v) {
			return v
		}
		p[v] = get(p[v])
		return p[v]
	}

	fun unite(v: Int, u: Int) {
		var vv = v
		var uu = u
		vv = get(vv)
		uu = get(uu)
		if (r.nextBoolean()) {
			p[vv] = uu
		} else {
			p[uu] = vv
		}
	}
}

private infix fun Int.towards(to: Int) = if (to > this) this..to else this downTo to
private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
private fun <T> Iterable<T>.cartesianTriangle() = withIndex().flatMap { x -> take(x.index).map { it to x.value } }
val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out, true))
}
