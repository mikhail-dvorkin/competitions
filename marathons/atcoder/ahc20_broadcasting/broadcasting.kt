package marathons.atcoder.ahc20_broadcasting

import java.io.*
import java.util.concurrent.Callable
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.sqrt

val EVALUATOR: Callable<Void?>
//		= marathons.utils.Evaluator(marathons.utils.atcoder.Visualizer(::solve)) //TESTING
		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT

fun solve(vertices: List<List<Int>>, edges: List<List<Int>>, people: List<List<Int>>, toVisualize: MutableList<Any>?): Pair<IntArray, MutableList<Int>> {
	fun sqr(x: Int) = x * x
	fun distSquared(person: Int, vertex: Int) = sqr(people[person][0] - vertices[vertex][0]) + sqr(people[person][1] - vertices[vertex][1])
	fun dist(person: Int, vertex: Int) = ceil(sqrt(distSquared(person, vertex).toDouble())).roundToInt()
	val closest = List(people.size) { person ->
		vertices.indices.sortedBy { vertex -> distSquared(person, vertex) }
	}
	val inf = Int.MAX_VALUE / 2
	val e = List(vertices.size) { IntArray(vertices.size) { inf } }
	val next = List(vertices.size) { IntArray(vertices.size) { it } }
	val nei = List(vertices.size) { mutableListOf<Int>() }
	val neiWeight = List(vertices.size) { mutableListOf<Int>() }
	val neiId = List(vertices.size) { IntArray(vertices.size) { -1 } }
	for (edge in edges.indices) {
		val (uIn, vIn, w) = edges[edge]
		val u = uIn - 1; val v = vIn - 1
		nei[u].add(v); neiWeight[u].add(w)
		nei[v].add(u); neiWeight[v].add(w)
		e[u][v] = w; e[v][u] = w
		neiId[u][v] = edge; neiId[v][u] = edge
	}
	for (k in vertices.indices) for (i in vertices.indices) for (j in vertices.indices) {
		if (e[i][k] + e[k][j] < e[i][j]) {
			e[i][j] = e[i][k] + e[k][j]
			next[i][j] = next[i][k]
		}
	}

	fun prim(verticesOn: BooleanArray): Pair<Long, MutableList<Int>> {
		verticesOn[0] = true
		val mark = BooleanArray(vertices.size)
		val dist = IntArray(vertices.size) { inf }
		val prev = IntArray(vertices.size)
		dist[0] = 0
		var score = 0L
		val usedEdges = mutableListOf<Int>()
		while (true) {
			val v = vertices.indices.filter { verticesOn[it] && !mark[it] }.minByOrNull { dist[it] }
				?: break
			var u = prev[v]
			while (true) {
				if (!mark[u]) {
					mark[u] = true
					for (w in vertices.indices) {
						if (dist[w] >= dist[u] + e[u][w]) {
							dist[w] = dist[u] + e[u][w]
							prev[w] = u
						}
					}
				}
				if (u == v) break
				val w = next[u][v]
				score += e[u][w]
				usedEdges.add(neiId[u][w])
				u = w
			}
		}
		return score to usedEdges
	}

	var ansScore = Long.MAX_VALUE
	var ansPowers = IntArray(vertices.size)
	var ansEdges = mutableListOf<Int>()

	val powers = IntArray(vertices.size)
	val audience = List(vertices.size) { mutableListOf<Int>() }

	fun check() {
		for (v in vertices.indices) {
			for (p in audience[v]) {
				require(distSquared(p, v) <= sqr(powers[v]))
				require(powers[v] > 0)
			}
		}
		require(audience.map { it.size }.sum() == people.size)
		require(audience.flatten().toSet().size == people.size)
	}

	fun score(): Long {
		val powersScore = powers.map { sqr(it).toLong() }.sum()
		val verticesOn = BooleanArray(vertices.size) { powers[it] > 0 && audience[it].isNotEmpty() }
		val (primScore, primEdges) = prim(verticesOn)
		val score = powersScore + primScore
		if (score < ansScore) {
			ansScore = score
			ansPowers = powers
			ansEdges = primEdges
		}
		System.err.println("$score = $powersScore pow + $primScore prim")
		check()
		return score
	}

	fun powers1() {
		for (person in people.indices) {
			val vertex = closest[person][0]
			powers[vertex] = powers[vertex].coerceAtLeast(dist(person, vertex))
			audience[vertex].add(person)
		}
	}

	fun powers2(magic: Int = 16): Boolean {
		var improve = false
		val cost = IntArray(people.size)
		val costHow = IntArray(people.size)
		for (vertex in vertices.indices.sortedBy { audience[it].size }) {
			for (person in audience[vertex]) {
				cost[person] = inf
				for (i in 0 until magic) {
					val u = closest[person][i]
					if (u == vertex) continue
					val costHere = sqr(dist(person, u)) - sqr(powers[u])
					if (costHere < cost[person]) {
						cost[person] = costHere
						costHow[person] = u
					}
				}
				cost[person] = cost[person].coerceAtLeast(0)
			}
			val newPower = mutableMapOf<Int, Int>()
			for (person in audience[vertex].sortedByDescending { cost[it] }) {
				if (cost[person] == 0) break
				var cost2 = inf
				var cost2how = -1
				for (i in 0 until magic) {
					val u = closest[person][i]
					if (u == vertex) continue
					val costHere = sqr(dist(person, u)) - sqr(newPower.getOrDefault(u, powers[u]))
					if (costHere < cost2) {
						cost2 = costHere
						cost2how = u
					}
				}
				val u = cost2how
				val costHere = sqr(dist(person, u)) - sqr(newPower.getOrDefault(u, powers[u]))
				if (costHere > 0) {
					newPower[u] = dist(person, u)
					require(newPower[u]!! > 0)
				}
			}
			val costOfPlan = newPower.entries.sumOf { sqr(it.value).toLong() - sqr(powers[it.key]) }
			if (costOfPlan <= sqr(powers[vertex])) {
				for (entry in newPower) {
					powers[entry.key] = entry.value
					require(powers[entry.key] > 0)
					require(entry.key != vertex)
				}
				powers[vertex] = 0
				for (person in audience[vertex]) {
					val u = closest[person].first { u ->
						powers[u] >= dist(person, u)
					}
					audience[u].add(person)
				}
				audience[vertex].clear()
				improve = true
				check()
			}
		}
		return improve
	}

	try {
		powers1()
		score()
		for (t in 0..9) {
			powers2()
			score()
		}
	} catch (_: TimeOutException) {
	}
	return ansPowers to ansEdges
}

class TimeOutException : RuntimeException()

fun solve(`in`: BufferedReader, out: BufferedWriter): List<Any>? {
	fun readLn() = `in`.readLine()!!
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()

	val (n, m, k) = readInts()
	val vertices = List(n) { readInts() }
	val edges = List(m) { readInts() }
	val people = List(k) { readInts() }
	val (powers, usedEdges) = solve(vertices, edges, people, toVisualize)
	val edgesOn = IntArray(edges.size)
	for (i in usedEdges) edgesOn[i] = 1
	out.write("${powers.joinToString(" ")}\n${edgesOn.joinToString(" ")}\n")
	out.close()
	return toVisualize
}

private inline fun debug(msg: () -> Any) { if (VERBOSE) println(msg()) }

fun main() {
	@Suppress("UNNECESSARY_SAFE_CALL")
	EVALUATOR?.call() ?: solve(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
