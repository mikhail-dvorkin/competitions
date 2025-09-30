package marathons.atcoder.ahc17_third2022_roadRepair

import java.io.*
import java.util.*
import java.util.concurrent.Callable
import kotlin.math.exp
import kotlin.random.Random

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(10, 0, false, RoadRepairEval()) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
@Suppress("ComplexRedundantLet")
val TIME_LIMIT = (6000 - 200)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
var timeStart: Long = 0
fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT

/**
 * Not smart: did not use maxRepairedPerDay, did not research at all.
 */
fun solve(graph: Graph, days: Int, maxRepairedPerDay: Int, toVisualize: MutableList<Any>?): IntArray {
	val tempAnswer = IntArray(graph.m)
	var tempTime = 0
	val penaltyGraph = List(graph.m) { mutableMapOf<Int, Int>() }
	fun addPenalty(id1: Int, id2: Int, penaltyValue: Int) {
		penaltyGraph[id1][id2] = penaltyValue + penaltyGraph[id1].getOrDefault(id2, 0)
		penaltyGraph[id2][id1] = penaltyValue + penaltyGraph[id2].getOrDefault(id1, 0)
	}
	for (v in graph.vertices) {
		for (i in graph.nei[v].indices) {
			val u = graph.nei[v][i]
			if (u < v) continue
			val vuId = graph.neiId[v][i]
			for (j in 0 until i) {
				val id = graph.neiId[v][j]
				addPenalty(vuId, id, 500 / graph.nei[v].size)
			}
			tempAnswer[vuId] = ++tempTime
			graph.dijkstra(v, tempAnswer, tempTime) ?: require(false)
			val path = graph.pathTo(u)
			for (id in path) {
				addPenalty(vuId, id, 1000 / path.size)
			}
		}
	}

	val random = Random(566)
	var coloring = Coloring(penaltyGraph, days, random)
	val settings = Settings()
	settings.temp0 = 10000.0
	coloring = simulatedAnnealing(coloring, settings, random) as Coloring
//	return IntArray(graph.m) { it % days }
	return coloring.a
}

fun Graph.dijkstra(vInit: Int, answer: IntArray?, day: Int, vDest: Int = -1): Long? {
	time++
	prev[vInit] = -1
	val treeSet = TreeSet<Long>()
	fun register(v: Int, newDist: Int) {
		if (distTime[v] == time) {
			treeSet.remove((dist[v].toLong() shl 32) + v)
		} else {
			distTime[v] = time
		}
		dist[v] = newDist
		treeSet.add((newDist.toLong() shl 32) + v)
	}
	register(vInit, 0)
	while (treeSet.isNotEmpty()) {
		val treeSetEntry = treeSet.pollFirst()!!
		val v = treeSetEntry.toInt()
		if (v == vDest) return 0L
		val distV = (treeSetEntry shr 32).toInt()
		mark[v] = time
		for (i in nei[v].indices) {
			if (day >= 0 && answer!![neiId[v][i]] == day) continue
			val u = nei[v][i]
			if (mark[u] == time) continue
			val w = neiWeight[v][i]
			val newDist = distV + w
			if (distTime[u] != time || newDist < dist[u]) {
				prev[u] = v
				register(u, newDist)
			}
		}
	}
	if (mark.any { it != time }) return null
	return dist.sumOf { it.toLong() }
}

fun Graph.idOfEdge(v: Int, u: Int) = neiId[v][nei[v].indexOf(u)]

fun Graph.pathTo(v: Int): List<Int> {
	val result = mutableListOf<Int>()
	var w = v
	while (prev[w] >= 0) {
		val u = prev[w]
		result.add(idOfEdge(u, w))
		w = u
	}
	return result.reversed()
}

fun Graph.calcFrustration(answer: IntArray?, day: Int): Long? {
	return vertices.sumOf { dijkstra(it, answer, day) ?: return null }
}

class Graph(edgesParam: List<List<Int>>, coordsParam: List<List<Int>>) {
	val n: Int
	val m: Int
	val coords: List<Pair<Int, Int>>
	val vertices: IntRange
	val nei: List<IntArray>
	val neiWeight: List<IntArray>
	val neiId: List<IntArray>
	val frustrationZero: Long
	val mark: IntArray
	val dist: IntArray
	val distTime: IntArray
	val prev: IntArray
	var time = 0

	init {
		n = coordsParam.size
		m = edgesParam.size
		coords = coordsParam.map { it.toPair() }
		vertices = 0 until n
		val neiList = List(n) { mutableListOf<Int>() }
		val neiWeightList = List(n) { mutableListOf<Int>() }
		val neiIdList = List(n) { mutableListOf<Int>() }
		for (i in edgesParam.indices) {
			val (vIn, uIn, w) = edgesParam[i]
			val v = vIn - 1; val u = uIn - 1
			neiList[v].add(u)
			neiWeightList[v].add(w)
			neiIdList[v].add(i)
			neiList[u].add(v)
			neiWeightList[u].add(w)
			neiIdList[u].add(i)
		}
		nei = neiList.map { it.toIntArray() }
		neiWeight = neiWeightList.map { it.toIntArray() }
		neiId = neiIdList.map { it.toIntArray() }
		mark = IntArray(n)
		dist = IntArray(n)
		distTime = IntArray(n)
		prev = IntArray(n)
		frustrationZero = calcFrustration(null, -1)!!
	}
}

fun read(`in`: BufferedReader): Triple<Graph, Int, Int> {
	fun readLn() = `in`.readLine()!!
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val (n, m, days, maxRepairedPerDay) = readInts()
	val edges = List(m) { readInts() }
	val coords = List(n) { readInts() }
	val graph = Graph(edges, coords)
	return Triple(graph, days, maxRepairedPerDay)
}

fun solveIO(`in`: BufferedReader, out: Writer): List<Any>? {
	timeStart = System.currentTimeMillis()
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()
	val (graph, days, maxRepairedPerDay) = read(`in`)

	out.write(solve(graph, days, maxRepairedPerDay, toVisualize).map { it + 1 }.joinToString(" "))
	out.close()
	return toVisualize
}

class Coloring : AnnealableWithStepBack {
	var n: Int
	var a: IntArray
	var penaltyGraph: List<MutableMap<Int, Int>>? = null
	var days: Int = 0
	var energy = 0.0
	var lastSwapA = 0
	var lastSwapB = 0
	var prevEnergy = 0.0

	constructor(penaltyGraph: List<MutableMap<Int, Int>>, days: Int, random: Random) {
		this.penaltyGraph = penaltyGraph
		this.days = days
		n = penaltyGraph.size
		a = IntArray(n) { it % days }
		a.shuffle(random)
		for (i in a.indices) {
			for (j in penaltyGraph[i].keys) {
				if (i < j && a[i] == a[j]) energy += penaltyGraph[i][j]!!
			}
		}
	}

	private constructor(n: Int, a: IntArray, energy: Double) {
		this.n = n
		this.a = a
		this.energy = energy
	}

	override fun cloneAnswer(): Coloring {
		return Coloring(n, a, energy)
	}

	override fun energy(): Double {
		return energy
	}

	override fun vary(random: Random) {
		prevEnergy = energy
		val i = random.nextInt(n)
		var j: Int
		while (true) {
			j = random.nextInt(n)
			if (a[i] != a[j]) {
				break
			}
		}
		lastSwapA = i
		lastSwapB = j

		for (k in penaltyGraph!![i].keys) {
			if (k == j) continue
			if (a[k] == a[i]) {
				energy -= penaltyGraph!![i][k]!!
			} else if (a[k] == a[j]) {
				energy += penaltyGraph!![i][k]!!
			}
		}
		for (k in penaltyGraph!![j].keys) {
			if (k == i) continue
			if (a[k] == a[j]) {
				energy -= penaltyGraph!![j][k]!!
			} else if (a[k] == a[i]) {
				energy += penaltyGraph!![j][k]!!
			}
		}

		val t = a[i]
		a[i] = a[j]
		a[j] = t
	}

	override fun undo() {
		energy = prevEnergy
		val t = a[lastSwapA]
		a[lastSwapA] = a[lastSwapB]
		a[lastSwapB] = t
	}

	override fun randomInstance(random: Random): AnnealableWithStepBack {
		return Coloring(penaltyGraph!!, days, random)
	}
}

interface Annealable {
	fun energy(): Double
	fun randomInstance(random: Random): Annealable?
}

interface AnnealableWithoutStepBack : Annealable {
	fun vary(random: Random): AnnealableWithoutStepBack?
}

interface AnnealableWithStepBack : Annealable {
	fun vary(random: Random)
	fun undo()
	fun cloneAnswer(): AnnealableWithStepBack?
}

class Settings @JvmOverloads constructor(
	var globalIterations: Int = 8192,
	var iterations: Int = 8192,
	var probStartWithPrevious: Double = 1 - 1.0 / 16,
	var recessionLimit: Int = Int.MAX_VALUE,
	var desiredEnergy: Double = -Double.MAX_VALUE,
	var temp0: Double = 1.0
)

fun simulatedAnnealing(itemStart: Annealable?, settings: Settings, r: Random): Annealable? {
	var item = itemStart
	val stepBack = item is AnnealableWithStepBack
	var energy = item!!.energy()
	var answerEnergy = Double.MAX_VALUE
	var answer: Annealable? = null
	for (glob in 0 until settings.globalIterations) {
		if (timePassed() > 1) return answer
		if (glob > 0 && r.nextDouble() >= settings.probStartWithPrevious) {
			item = item!!.randomInstance(r)
			energy = item!!.energy()
		}
		var end = settings.iterations
		var iter = 1
		var recession = 0
		while (true) {
			if (energy < answerEnergy) {
				answerEnergy = energy
				answer = if (stepBack) {
					(item as AnnealableWithStepBack?)!!.cloneAnswer()
				} else {
					item
				}
				if (answerEnergy <= settings.desiredEnergy) {
					return answer
				}
				end = end.coerceAtLeast(iter + settings.iterations)
			}
			if (iter > end) {
				break
			}
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
				//TODO better:
				//t = t_start * (t_final / t_start) ** part_of_time_passed
				//p = exp((cur_result - new_result) / t)
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

private fun <T> List<T>.toPair() = get(0) to get(1)

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solveIO(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
