package marathons.atcoder.ahc16_hackToTheFuture2023_qual_graphorean

import kotlin.math.*
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val submit = System.getProperty("sun.java.command", "").contains("MainKt")
const val TIME_LIMIT = 5000 - 250
const val max_n = 100
const val max_m = 100
const val min_n = 4
@Suppress("unused") const val min_m = 10
@Suppress("unused") const val max_errorRate = 0.4
const val defaultQuestions = 100
val eval = Triple(max_m, (0..40 step 2), defaultQuestions)
//val eval = Triple(10..100 step 10, 0..20, 1)
//val eval = Triple(100, 2, 99)
val stopOnWrong = !submit && eval.third < defaultQuestions
val verbose = stopOnWrong

fun solve(m: Int, errorRate: Double): Solution {
	if (m >= 100 - 400 * (errorRate - 0.33)) return SurrenderSolution(m)
	val addToMinClique = (errorRate / 0.04).toInt()
	return CliqueSolution(m, errorRate, addToMinClique)
}

class CliqueSolution(val m: Int, private val errorRate: Double, addToMinClique: Int) : Solution {
	val n: Int
	override val graphRepresentations: List<String>
	override val partitions: MutableList<IntArray>
	private val minClique: Int

	val timeStart = System.currentTimeMillis()

	override fun whichOne(graphRepresentation: String, iter: Int): Int {
		val timePassed = System.currentTimeMillis() - timeStart
		if (timePassed > TIME_LIMIT) return 0
		val hurry = timePassed > TIME_LIMIT / 2
		debug { graphRepresentation }
		val eInit = graphRepresentationToMatrix(graphRepresentation)
		require(eInit.size == n)

		var e = eInit
		prettyWrite(e, "init")
		var ts = System.currentTimeMillis()
		e = improveByProbabilities(e, errorRate - 0.1)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		e = removeStupidEdges(e, 1)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		if (!hurry) e = cutBadArticulation(e)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		e = fixIsolated(e, eInit)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		e = addObviousEdges(e)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		e = removeStupidEdges(e, if (n == max_n || hurry) 1 else 2)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		if (!hurry) e = cutBadArticulation(e)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		e = graduateDescent(e, eInit)
		debug { "${System.currentTimeMillis() - ts}ms" }; ts = System.currentTimeMillis()
		val compSizes = compSizes(e)
		return guessByComponentSizes(compSizes)
	}

	init {
		val (n_, partitions_) = generatePartitions(minClique() + addToMinClique)
		n = n_
		partitions = partitions_
		minClique = partitions.minOf { it.min() }
		debug { "$minClique ${partitions.map { it.contentToString() }}" }
		graphRepresentations = partitions.map { partition ->
			val e = List(n) { BooleanArray(n) }
			var k = 0
			for (c in partition) {
				for (i in 0 until c) for (j in 0 until i) {
					e[k + i][k + j] = true
					e[k + j][k + i] = true
				}
				k += c
			}
			graphMatrixToRepresentation(e)//.also { debug(it) }
		}
	}

	private fun editDists(e: List<BooleanArray>): List<IntArray> {
		val editDists = List(n) { IntArray(n) }
		for (i in 0 until n) for (j in 0 until i) {
			editDists[i][j] = (0 until n).count { k -> (k != i) && (k != j) && e[i][k] != e[j][k] } +
					if (e[i][j]) 0 else 1
			editDists[j][i] = editDists[i][j]
		}
		return editDists
	}

	private fun dists(e: List<BooleanArray>): List<IntArray> {
		val dists = List(n) { IntArray(n) }
		for (i in 0 until n) for (j in 0 until i) {
			dists[i][j] = (0 until n).count { k -> (k != i) && (k != j) && !(e[i][k] && e[j][k]) } +
					if (e[i][j]) 0 else 1
			dists[j][i] = dists[i][j]
		}
		return dists
	}

	private fun improveByProbabilities(e: List<BooleanArray>, threshold: Double): List<BooleanArray> {
		val forSame = (n - 1) * errorRate
		val forDifferent = (n - 1) * errorRate + (2 * minClique - 1) * (1 - 2 * errorRate)
		debug { "threshold: $forSame $forDifferent" }

		val editDists = editDists(e)
		val f = List(n) { BooleanArray(n) }
		for (i in 0 until n) for (j in 0 until i) {
//				debug("dist[$i][$j] = $dist")
			if (editDists[i][j] < forSame + threshold * (forDifferent - forSame)) {
				f[i][j] = true
			} else if (editDists[i][j] > forSame + (1 - threshold) * (forDifferent - forSame)) {
				f[i][j] = false
			} else {
				f[i][j] = e[i][j]
			}
			f[j][i] = f[i][j]
		}
		prettyWrite(f, "improveByProbabilities")
		return f
	}

	private fun addObviousEdges(e: List<BooleanArray>): List<BooleanArray> {
		val f = e.clone()
		if (minClique == 1) return e
		val dists = dists(e)
		val inQuasiClique = List(n) { BooleanArray(n) }
		for (v in 0 until n) {
			val q = (0 until n).sortedBy { dists[v][it] }.take(minClique)
			for (u in q) inQuasiClique[v][u] = true
		}
//		val sb = StringBuilder()
		for (v in 0 until n) {
			for (u in 0 until v) {
				if (!e[v][u] && (inQuasiClique[v][u] || inQuasiClique[u][v])) {
//					if (!submit) sb.append("WOW $v $u\t")
					f[v][u] = true
					f[u][v] = true
				}
			}
		}
//		if (sb.isNotEmpty()) debug(sb)
		prettyWrite(f, "addObviousEdges")
		return f
	}

	private tailrec fun removeStupidEdges(e: List<BooleanArray>, maxRepeats: Int): List<BooleanArray> {
		val f = e.clone()
		if (minClique == 1) return e
		val dists = dists(e)
		val inQuasiClique = List(n) { BooleanArray(n) }
		val quasiCliques = List(n) { v ->
			val q = (0 until n).sortedBy { dists[v][it] }.take(minClique).toIntArray()
			for (u in q) inQuasiClique[v][u] = true
			q
		}
//		val sb = StringBuilder()
		var improved = false
		for (v in 0 until n) {
			for (u in 0 until v) {
				if (e[v][u] && (!inQuasiClique[v][u] && !inQuasiClique[u][v])) {
					var score = 0
					for (w in quasiCliques[v]) if (w != v && e[u][w]) score++
					for (w in quasiCliques[u]) if (w != u && e[v][w]) score++
					if (score >= minClique - 1) continue
//					if (!submit) sb.append("UN${}WOW $v $u\t")
					f[v][u] = false
					f[u][v] = false
					improved = true
				}
			}
		}
//		if (sb.isNotEmpty()) debug(sb)
		prettyWrite(f, "removeStupidEdges")
		if (improved && maxRepeats > 1) return removeStupidEdges(f, maxRepeats - 1)
		return f
	}

	private fun fixIsolated(e: List<BooleanArray>, eInit: List<BooleanArray>): List<BooleanArray> {
		if (minClique == 1) return e
		val f = e.clone()
		val deg = e.map { row -> row.count { it } }
		val toWhom = deg.indices.filter { deg[it] > 0 }.minByOrNull { deg[it] } ?: error("")
		for (v in 0 until n) {
			if (deg[v] > 0) continue
			debug { "ISOLATED $v, connected to $toWhom\t//${eInit[v].contentToString()}" }
			f[v][toWhom] = true
			f[toWhom][v] = true
		}
		prettyWrite(f, "fixIsolated")
		return f
	}

	private fun cutBadArticulation(e: List<BooleanArray>): List<BooleanArray> {
		val skip = BooleanArray(n)
		for (c in components(e)) {
			if (c.size < 2 * minClique) {
				for (v in c) skip[v] = true
			}
		}
		val f = e.clone()
		val mark = BooleanArray(n)
		for (v in 0 until n) {
			if (skip[v]) continue
			mark.fill(false)
			val comps = mutableListOf<List<Int>>()
			val comp = mutableListOf<Int>()
			fun dfs(u: Int) {
				mark[u] = true
				comp.add(u)
				for (w in 0 until n) {
					if (!f[u][w] || mark[w] || w == v) continue
					dfs(w)
				}
			}
			for (u in 0 until n) {
				if (!f[v][u] || mark[u]) continue
				comp.clear()
				dfs(u)
				comps.add(comp.toList())
			}
			val largeCount = comps.count { it.size >= minClique - 1 }
			if (largeCount >= 2) {
				debug { "ARTICULATION $v" }
				if (skip[v]) {
					debug { "OPPA" }
				}
				val dangerousCount = comps.count { it.size == minClique - 1 }
				var leaveOnly: List<Int>? = null
				if (dangerousCount >= 1) {
					debug { "DANGEROUS ARTICULATION $v" }
					if (dangerousCount == 1) {
						leaveOnly = comps.first { it.size == minClique - 1 }
					}
				} else {
					//if (mode == 10) {
					leaveOnly = comps.filter { it.size >= minClique - 1 }.minByOrNull { c ->
						val part = c.count { u -> f[v][u] } * 1.0 / c.size
						debug { "$c $part" }
						-part
					}!!
				}
				leaveOnly ?: continue
				for (c in comps) {
					if (c[0] == leaveOnly[0]) continue
					if (c.size < minClique) continue
					for (u in c) {
						f[v][u] = false
						f[u][v] = false
					}
				}
			}
 //			debug("ISOLATED $v, connected to $toWhom\t//${eInit[v].contentToString()}")
//			f[v][toWhom] = true
//			f[toWhom][v] = true
		}
		prettyWrite(f, "cutBadArticulation")
		return f
	}

	private tailrec fun graduateDescent(e: List<BooleanArray>, eInit: List<BooleanArray>): List<BooleanArray> {
		val comps = components(e)
		val color = IntArray(n)
		comps.withIndex().forEach { component ->
			for (v in component.value) color[v] = component.index
		}
		for (v in 0 until n) {
			fun quality(newColor: Int): Int {
				var result = 0
				for (u in 0 until n) {
					if (u == v) continue
					if (e[u][v] == (color[u] == newColor)) result++
				}
				return result
			}

			val oldColor = color[v]
			val bestColor = comps.indices.minByOrNull { -quality(it) }!!
			if (quality(bestColor) != quality(oldColor)) {
				debug { "IMPROVE $v $oldColor -> $bestColor\t//$comps" }
				color[v] = bestColor
				val f = List(n) { i -> BooleanArray(n) { j ->
					color[i] == color[j] && i != j
				} }
				prettyWrite(f, "graduateDescent")
				return graduateDescent(f, eInit)
			}
		}
		return e
	}

	@Deprecated("")
	private fun graduateDescentOld(e: List<BooleanArray>): Int {
		val used = BooleanArray(n)
		val components = mutableListOf<Int>()
		for (v in 0 until n) {
			if (used[v]) continue
			val these = BooleanArray(n)
			these[v] = true
			fun scoreOfThese(): Double {
				var correct = 0
				var total = 0
				for (i in 0 until n) for (j in 0 until i) {
					if (these[i] && these[j]) {
						total++
						if (e[i][j]) correct++
					} else if (these[i] xor these[j]) {
						total++
						if (!e[i][j]) correct++
					}
				}
				debug { "$correct\t$total\t" }
				return abs(1 - correct * 1.0 / total)
			}
			do {
				var bestScore = scoreOfThese()
				var bestU = -1
				for (u in 0 until n) {
					if (used[u] || u == v) continue
					these[u] = !these[u]
					val newScore = scoreOfThese()
					debug { "$bestScore -> $newScore" }
					if (newScore < bestScore) {
						bestScore = newScore
						bestU = u
					}
					these[u] = !these[u]
				}
				if (bestU == -1) break
				these[bestU] = !these[bestU]
				debug { these.contentToString() }
			} while (true)
			var component = 0
			for (u in 0 until n) if (these[u]) {
				used[u] = true
				component++
			}
			components.add(component)
			debug { components }
		}
		return guessByComponentSizes(components)
	}

	private fun guessByComponentSizes(componentsList: List<Int>): Int {
		val components = componentsList.sorted()
		val guess = (0 until m).minBy { i ->
			if (partitions[i].size != components.size) return@minBy Int.MAX_VALUE
			components.indices.sumOf { abs(partitions[i][it] - components[it]) }
		}
		return guess
	}

	private fun generatePartitions(theoreticalMinClique: Int): Pair<Int, MutableList<IntArray>> {
		debug { "theoreticalMinClique $theoreticalMinClique" }
		for (q in theoreticalMinClique until max_n + theoreticalMinClique) {
			val nn = q.coerceAtMost(max_n)
			val cc = theoreticalMinClique - (q - max_n).coerceAtLeast(0)
			val pool = mutableListOf<IntArray>()
			findPartitions(nn, cc, mutableListOf(), pool)
			if (pool.size == m) return nn to pool
		}
		error("")
	}

	private fun findPartitions(nn: Int, cc: Int, prefix: MutableList<Int>, pool: MutableList<IntArray>) {
		if (pool.size == m) return
		if (nn == 0) {
			pool.add(prefix.toIntArray())
			return
		}
		if (nn < cc) return
		for (c in cc..nn) {
			prefix.add(c)
			findPartitions(nn - c, c, prefix, pool)
			prefix.removeLast()
		}
	}

	/**
	 * 11111000000…l
	 * 00000111110…
	 * ≈Always distinguish 2k zeroes from 2k ones.
	 * cnk(2k, k) * p^k * (1-p)^k << 0.01
	 */
	private fun minClique(): Int {
		if (errorRate == 0.0) return 1
		for (k in 3..max_n) {
			val p = (k..2 * k).sumOf { cnk(2 * k, it) * errorRate.pow(it) * (1 - errorRate).pow(2 * k - it) }
			if (p < 0.003) return k
//			debug("$k\t$p")
		}
		return max_n
	}
}

class SurrenderSolution(val m: Int) : Solution {
	override val graphRepresentations: List<String>
	override val partitions: List<IntArray>

	init {
		val n = min_n
		val length = n * (n - 1) / 2
		graphRepresentations = List(m) { i ->
			List(length) { if (2 * i >= m/*it * m < i * length*/) 1 else 0 }.joinToString("")
		}
		partitions = graphRepresentations.map { intArrayOf(it.count { c -> c == '1' }) }
	}

	override fun whichOne(graphRepresentation: String, iter: Int): Int {
		val s = graphRepresentation.count { it == '1' }
		return (s * 1.0 / graphRepresentation.length * (m - 1)).roundToInt()
	}
}

interface Solution {
	val graphRepresentations: List<String>
	val partitions: List<IntArray>
	fun whichOne(graphRepresentation: String, iter: Int): Int
}


fun evaluate(m: Int, errorRate: Double, questions: Int = defaultQuestions): Double {
	debug { "=== Evaluate $m $errorRate $questions ===" }
	val solution = solve(m, errorRate)
	val graphs = solution.graphRepresentations
	require(graphs.size == m)
	val n = graphRepresentationLengthToSize(graphs[0].length)
	if (submit) {
		println(n)
		println(graphs.joinToString("\n"))
	}
	var errors = 0
	repeat(questions) {
		val x: Int
		val questionedGraph = if (submit) {
			x = -1
			readLn()
		} else {
			val randomForEval = Random(it)
			x = randomForEval.nextInt(m)
			debug { "Pssst, secret: ${solution.partitions[x].contentToString()}" }
			val xGraph = graphRepresentationToMatrix(graphs[x])
			val graph = graphShuffled(xGraph, errorRate, randomForEval)
			graphMatrixToRepresentation(graph)
		}
		val y = solution.whichOne(questionedGraph, it)
		if (submit) println(y)
		if (x != y) {
			errors++
			if (stopOnWrong) exitProcess(0)
		}
	}
	val score = (1e9 * 0.9.pow(defaultQuestions.toDouble() * errors / questions) / n)
	evaluationResults.add("$m\t${(errorRate * 100).roundToInt()}%  \t$n\t$$errors\t${score.roundToInt()}")
	return score
}

fun graphRepresentationLengthToSize(len: Int) = ceil((len * 2.0).pow(0.5)).toInt()

fun graphRepresentationToMatrix(s: String): List<BooleanArray> {
	val n = graphRepresentationLengthToSize(s.length)
	val e = List(n) { BooleanArray(n) }
	var k = 0
	for (i in 0 until n) for (j in i + 1 until n) {
		e[i][j] = s[k++] == '1'
		e[j][i] = e[i][j]
	}
	return e
}

fun graphMatrixToRepresentation(e: List<BooleanArray>): String {
	val n = e.size
	val sb = StringBuffer(n * (n - 1) / 2)
	for (i in 0 until n) for (j in i + 1 until n) sb.append(if (e[i][j]) "1" else "0")
	return sb.toString()
}

fun compSizes(e: List<BooleanArray>): List<Int> {
	val mark = BooleanArray(e.size)
	var currentSize = 0
	fun dfs(v: Int) {
		mark[v] = true
		currentSize++
		for (u in e.indices) if (e[v][u] && !mark[u]) dfs(u)
	}
	val result = mutableListOf<Int>()
	for (i in e.indices) {
		if (mark[i]) continue
		currentSize = 0
		dfs(i)
		result.add(currentSize)
	}
	return result.sorted()
}

fun components(e: List<BooleanArray>): List<List<Int>> {
	val mark = BooleanArray(e.size)
	val comp = mutableListOf<Int>()
	fun dfs(v: Int) {
		mark[v] = true
		comp.add(v)
		for (u in e.indices) if (e[v][u] && !mark[u]) dfs(u)
	}
	val result = mutableListOf<List<Int>>()
	for (i in e.indices) {
		if (mark[i]) continue
		comp.clear()
		dfs(i)
		result.add(comp.toList())
	}
	return result
}

fun prettyWrite(e: List<BooleanArray>, name: String) {
	if (!verbose) return
	/*
	for (i in e.indices) {
		println(e.sortedBy { it.contentToString() }[i].joinToString("") { if (it) "#" else "." })
	}
	 */
	print ("graph $name${compSizes(e).joinToString("_")} { ")
	for (i in e.indices) for (j in 0 until i) if (e[i][j]) print("v$i -- v$j; ")
	println("}")
}

fun graphShuffled(e: List<BooleanArray>, errorRate: Double, random: Random): List<BooleanArray> {
	val p = e.indices.shuffled(random)
	return List(e.size) { i -> BooleanArray(e.size) { j ->
		e[p[i]][p[j]] xor (random.nextDouble() < errorRate)
	} }
}

fun factorial(n: Int): Double {
	if (n < 2) return 1.0
	return n * factorial(n - 1)
}

fun cnk(n: Int, k: Int): Double {
	return factorial(n) / factorial(k) / factorial(n - k)
}

private fun List<BooleanArray>.clone() = this.map { it.clone() }

val evaluationResults = mutableListOf<String>()

@OptIn(ExperimentalTime::class)
fun main() {
	if (submit) {
		val (mIn, errorRateIn) = readStrings()
		evaluate(mIn.toInt(), errorRateIn.toDouble())
		return
	}
	var totalScore = 0.0
	val (ms, rates, questions) = eval
	var maxTime = measureTime {}
	for (m in ms.asRange()) {
		for (r in rates.asRange()) {
			val time = measureTime {
				totalScore += evaluate(m, r * 0.01, questions)
			}
			maxTime = maxTime.coerceAtLeast(time)
		}
	}
	println(maxTime.toString(DurationUnit.SECONDS, 2))
	println(evaluationResults.joinToString("\n"))
	println(totalScore * 1e-7)
}

private fun IntProgression.asRange() = this
private fun Int.asRange() = this..this
private inline fun debug(msg: () -> Any) { if (verbose) println(msg()) }
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
