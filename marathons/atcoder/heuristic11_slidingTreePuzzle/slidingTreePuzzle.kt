package marathons.atcoder.ahc11_slidingTreePuzzle

import java.util.*
import kotlin.math.*
import kotlin.reflect.KFunction2

const val onlineJudge = true
const val timeAnnealing = 2750
const val test = "6 432\n62ce43\na068f9\na89da9\n5d93cb\n276253\n424ba8"

fun main() {
	if (!onlineJudge) setIn(test)
	val (n, maxMoves) = readInts()
	val fieldStrings = List(n) { readLn() }
	timeStart = System.currentTimeMillis()
	val field = fieldStrings.map { row -> IntArray(n) { row[it].digitToInt(16) } }
	val fieldCountTypes = IntArray(16)
	for (y in field.indices) for (x in field.indices) fieldCountTypes[field[y][x]]++
	debug(fieldCountTypes.contentToString())
	debug(fieldCountTypes.sum().toString() + "\n")
	val settings = Settings()
	val answer = (simulatedAnnealing(Permutation(field.map { it.clone() }), settings, random) as Permutation).field
	val path = solveFifteen(field, answer)
	debug("\nTime: ${System.currentTimeMillis() - timeStart}\n")
	println(path.take(maxMoves))
}

fun solveFifteen(field: List<IntArray>, answer: List<IntArray>): String {
	debug("\n" + field.map { it.contentToString() })
	debug("\n" + answer.map { it.contentToString() })
	var y0 = field.indexOfFirst { it.contains(0) }
	var x0 = field[y0].indexOf(0)
	var yTake = -1
	var xTake = -1

	val queueY = IntArray(field.size * field.size)
	val queueX = IntArray(field.size * field.size)
	val mark = List(field.size) { IntArray(field.size) }
	val how = List(field.size) { IntArray(field.size) }
	var markIter = 0

	val path = StringBuilder()
	fun makeMove(dy: Int, dx: Int) {
		path.append(moveChar(dy, dx))
		field[y0][x0] = field[y0 + dy][x0 + dx]
		y0 += dy; x0 += dx
		field[y0][x0] = 0
	}

	fun moveZero(yWant: Int, xWant: Int, allowed: KFunction2<Int, Int, Boolean>) {
		markIter++
		queueY[0] = y0
		queueX[0] = x0
		var queueLow = 0
		var queueHigh = 1
		mark[y0][x0] = markIter
		while (mark[yWant][xWant] != markIter) {
			if (queueLow == queueHigh) {
				error("")
			}
			val y = queueY[queueLow]
			val x = queueX[queueLow]
			queueLow++
			for (d in DY.indices) {
				val yy = y + DY[d]; val xx = x + DX[d]
				if (yy !in field.indices || xx !in field.indices) continue
				if (mark[yy][xx] == markIter) continue
				if (!allowed(yy, xx)) continue
				queueY[queueHigh] = yy
				queueX[queueHigh] = xx
				queueHigh++
				mark[yy][xx] = markIter
				how[yy][xx] = d
			}
		}
		var y = yWant
		var x = xWant
		val way = mutableListOf<Int>()
		while (y != y0 || x != x0) {
			val d = how[y][x]
			way.add(d)
			if (way.size > queueX.size) {
				error("")
			}
			y -= DY[d]; x -= DX[d]
		}
		for (d in way.reversed()) {
			makeMove(DY[d], DX[d])
		}
		require(y0 == yWant)
		require(x0 == xWant)
	}
	fun moveTake(dy: Int, dx: Int, allowed: KFunction2<Int, Int, Boolean>) {
		moveZero(yTake + dy, xTake + dx, allowed)
		makeMove(-dy, -dx)
		yTake += dy; xTake += dx
	}
	fun specific(s: String) {
		for (c in s) { val d = DIR.indexOf(c); makeMove(DY[d], DX[d]) }
	}

	fun take(yDesired: Int, xDesired: Int, reversed: Boolean) {
		val desired = answer[yDesired][xDesired]
		if (field[yDesired][xDesired] == desired) return
		yTake = -1; xTake = -1; var bestDist = 3 * field.size
		for (y in yDesired until field.size) for (x in field.indices) {
			if (y == yDesired && ((x < xDesired) xor reversed) || field[y][x] != desired) continue
			val dist = abs(y - yDesired) + abs(x - xDesired)
			if (dist < bestDist) {
				bestDist = dist; yTake = y; xTake = x
			}
		}
		fun canUse(y: Int, x: Int): Boolean {
			if (y < yDesired) return false
			if (y == yDesired && ((x < xDesired) xor reversed) && x != xDesired) return false
			if (y == yTake && x == xTake) return false
			return true
		}
		while (xTake < xDesired) moveTake(0, 1, ::canUse)
		while (xTake > xDesired) moveTake(0, -1, ::canUse)
		if (xDesired != if (reversed) 0 else field.size - 1) {
			while (yTake > yDesired) moveTake(-1, 0, ::canUse)
		} else {
			while (yTake > yDesired + 1) moveTake(-1, 0, ::canUse)
			if (y0 == yDesired) return specific("D")
			moveZero(yDesired + 1, if (reversed) 1 else field.size - 2, ::canUse)
			@Suppress("SpellCheckingInspection")
			specific(if (reversed) "RULLDRURD" else "LURRDLULD")
		}
	}

	fun take2(xDesired: Int) {
		val top = field.size - 2; val bottom = field.size - 1
		if (answer[bottom][xDesired] == field[bottom][xDesired] && answer[top][xDesired] == field[top][xDesired]) return
		if (x0 == xDesired) specific("R")
		val desiredBottom = answer[bottom][xDesired]
		if (field[top][xDesired] != desiredBottom) {
			yTake = -1; xTake = -1; var bestDist = 3 * field.size
			for (y in top..bottom) for (x in xDesired until field.size) {
				if (field[y][x] != desiredBottom) continue
				val dist = abs(y - top) + abs(x - xDesired)
				if (dist < bestDist) {
					bestDist = dist; yTake = y; xTake = x
				}
			}
			fun canUse(y: Int, x: Int): Boolean {
				if (y < top || x < xDesired) return false
				if (y == yTake && x == xTake) return false
				return true
			}
			if (yTake == bottom) moveTake(-1, 0, ::canUse)
			while (xTake > xDesired) moveTake(0, -1, ::canUse)
		}
		val desiredTop = answer[top][xDesired]
		if (x0 == xDesired) specific("R")
		if (field[bottom][xDesired] == desiredTop) {
			fun canUse(y: Int, x: Int): Boolean {
				if (y < top || x < xDesired) return false
				return true
			}
			if (desiredTop != desiredBottom) {
				moveZero(top, xDesired + 1, ::canUse)
				@Suppress("SpellCheckingInspection")
				specific("LDRRULDLURRDLULDR")
			}
		} else {
			yTake = -1; xTake = -1; var bestDist = 3 * field.size
			for (y in top..bottom) for (x in xDesired + 1 until field.size) {
				if (field[y][x] != desiredTop) continue
				val dist = abs(y - top) + abs(x - xDesired)
				if (dist < bestDist) {
					bestDist = dist; yTake = y; xTake = x
				}
			}
			fun canUse(y: Int, x: Int): Boolean {
				if (y < top || x <= xDesired) return false
				if (y == yTake && x == xTake) return false
				return true
			}
			if (yTake == bottom) moveTake(-1, 0, ::canUse)
			while (xTake > xDesired + 1) moveTake(0, -1, ::canUse)

			fun canUse2(y: Int, x: Int): Boolean {
				if (y < top || x < xDesired) return false
				if (y == top && x <= xDesired + 1) return false
				return true
			}
			moveZero(bottom, xDesired, ::canUse2)
			specific("UR")
		}
	}

	fun take3() {
		val top = field.size - 2; val bottom = field.size - 1
		val desired = answer[top][top]
		fun canUse(y: Int, x: Int): Boolean {
			if (y < top || x < top) return false
			return true
		}
		if (field[top][top] == desired) return moveZero(bottom, bottom, ::canUse)
		moveZero(top, top, ::canUse)
		if (field[top][bottom] == desired) return specific("RD")
		if (field[bottom][top] == desired) return specific("DR")
		@Suppress("SpellCheckingInspection")
		specific("DRULDR")
	}

	for (yDesired in 0..field.size - 3) {
//	for (yDesired in 0..3) {
		val reversed = yDesired % 2 == 1
		for (xDesired in if (reversed) field.indices.reversed() else field.indices) {
//		for (xDesired in 0..field.size - 2) {
			debug("take($yDesired, $xDesired)\n")
			take(yDesired, xDesired, reversed)
		}
	}
	for (xDesired in 0..field.size - 3) {
//	for (xDesired in 0..2) {
		debug("take2($xDesired)\n")
		take2(xDesired)
	}
	take3()
	return path.toString()

}

var timeStart = 0L
val random = Random(566)

val DY = intArrayOf(0, -1, 0, 1)
val DX = intArrayOf(-1, 0, 1, 0)
@Suppress("SpellCheckingInspection")
const val DIR = "LURD"
fun moveChar(dy: Int, dx: Int) = DIR[DY.indices.first { DY[it] == dy && DX[it] == dx }]

class Permutation(val field: List<IntArray>) : AnnealableWithoutStepBack {
	private val energy: Double

	init {
		normalizeZero()
		energy = countEnergy()
	}

	private fun normalizeZero() {
		val y0 = field.indexOfFirst { it.contains(0) }
		val x0 = field[y0].indexOf(0)
		field[y0][x0] = field[field.lastIndex][field.lastIndex]
		field[field.lastIndex][field.lastIndex] = 0
	}

	private fun countEnergy(): Double {
		val mark = List(field.size) { BooleanArray(field.size) }
		var componentSize = 0
		var outHalves = 0
		fun dfs(y: Int, x: Int) {
			mark[y][x] = true
			componentSize++
			val f = field[y][x]
			for (d in DY.indices) {
				if (((f shr d) and 1) == 0) continue
				val yy = y + DY[d]; val xx = x + DX[d]
				if (xx !in field.indices || yy !in field.indices || mark[yy][xx]) continue
				if (((field[yy][xx] shr (d xor 2)) and 1) == 0) { outHalves++; continue }
				dfs(yy, xx)
			}
		}
		val componentEnergies = mutableListOf<Double>()
		for (y in field.indices) for (x in field.indices) {
			if (mark[y][x] || field[y][x] == 0) continue
			componentSize = 0
			outHalves = 0
			dfs(y, x)
			val componentEnergy = componentSize * 1.0
			componentEnergies.add(componentEnergy)
		}
		val componentBonus = componentEnergies.sortedDescending().withIndex().sumOf { it.value * 0.5.pow(it.index.toDouble()) }
		val cornerPenalty = if (field[field.lastIndex - 1][field.lastIndex] != field[field.lastIndex][field.lastIndex - 1]) 1 else 0
//		debug(componentEnergies)
		return field.size * field.size - 1 - componentBonus + cornerPenalty
	}

	override fun vary(random: Random?): Permutation {
		val (y1, x1) = generateSequence { List(2) { random!!.nextInt(field.size) } }
			.first { val (y1, x1) = it; field[y1][x1] != 0 }
		val (y2, x2) = generateSequence { List(2) { random!!.nextInt(field.size) } }
			.first { val (y2, x2) = it; field[y2][x2] != 0 && field[y2][x2] != field[y1][x1] }
		val newField = field.map { it.clone() }
		val t = newField[y1][x1]; newField[y1][x1] = newField[y2][x2]; newField[y2][x2] = t
		return Permutation(newField)
	}

	override fun energy() = energy
	override fun randomInstance(random: Random?) = Permutation(field.shuffled(random!!))
}

fun List<IntArray>.shuffled(random: Random): List<IntArray> {
	val array = map { it.toList() }.flatten()
	return array.shuffled(random).windowed(size, size).map { it.toIntArray() }
}

interface Annealable {
	fun energy(): Double
	fun randomInstance(random: Random?): Annealable?
}

interface AnnealableWithoutStepBack : Annealable {
	fun vary(random: Random?): AnnealableWithoutStepBack?
}

interface AnnealableWithStepBack : Annealable {
	fun vary(random: Random?)
	fun undo()
	fun cloneAnswer(): AnnealableWithStepBack?
}

class Settings constructor(
	var globalIterations: Int = 1 shl 30,
	var iterations: Int = 1 shl 16,
	var probStartWithPrevious: Double = 1 - 1.0 / 16,
	var recessionLimit: Int = Int.MAX_VALUE,
	var desiredEnergy: Double = 0.0,
	var temp0: Double = 200.0
)

fun simulatedAnnealing(itemInit: Annealable, settings: Settings, r: Random): Annealable? {
	var item: Annealable? = itemInit
	val stepBack = item is AnnealableWithStepBack
	var energy = item!!.energy()
	var answerEnergy = Double.MAX_VALUE
	var answer: Annealable? = null
	for (glob in 0 until settings.globalIterations) {
		if (glob > 0 && r.nextDouble() >= settings.probStartWithPrevious) {
			item = item!!.randomInstance(r)
			energy = item!!.energy()
		}
		var end = settings.iterations
		var iter = 1
		var recession = 0
		while (true) {
			if (energy < answerEnergy) {
				debug("\n$energy at $glob/$iter")
				answerEnergy = energy
				answer = if (stepBack) {
					(item as AnnealableWithStepBack?)!!.cloneAnswer()
				} else {
					item
				}
				if (answerEnergy <= settings.desiredEnergy) {
					return item
				}
				end = maxOf(end, iter + settings.iterations)
			}
			if (iter > end) {
				break
			}
			if (System.currentTimeMillis() > timeStart + timeAnnealing) return answer
			var nextEnergy: Double
			var next: AnnealableWithoutStepBack? = null
			if (stepBack) {
				(item as AnnealableWithStepBack?)!!.vary(r)
				nextEnergy = item!!.energy()
			} else {
				next = (item as AnnealableWithoutStepBack?)!!.vary(r)
				nextEnergy = next!!.energy()
			}
			val dEnergy = nextEnergy - energy
			var accept: Boolean
			if (dEnergy < 0) {
				accept = true
				recession = 0
			} else {
				recession++
				if (recession == settings.recessionLimit) {
					break
				}
				val barrier = exp(-1.0 * dEnergy * iter / settings.temp0)
				accept = r.nextDouble() < barrier
			}
			if (accept) {
				if (!stepBack) {
					assert(next != null)
					item = next
				}
				energy = nextEnergy
			} else {
				if (stepBack) {
					(item as AnnealableWithStepBack?)!!.undo()
				}
			}
			iter++
		}
	}
	return answer
}

class DisjointSetUnion(n: Int) {
	var p: IntArray

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
		if (random.nextBoolean()) {
			p[vv] = uu
		} else {
			p[uu] = vv
		}
	}
}

private fun debug(s: String) { if (!onlineJudge) print("$s ") }

@Suppress("SameParameterValue")
private fun setIn(input: String) = System.setIn(input.byteInputStream())
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
