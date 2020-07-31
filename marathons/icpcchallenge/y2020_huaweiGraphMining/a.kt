package marathons.icpcchallenge.y2020_huaweiGraphMining

import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintWriter
import java.util.*
import kotlin.math.pow
import kotlin.random.asJavaRandom

fun solve(fileName: String, nei: List<IntArray>) {
	val outputFile = "$fileName.out"
	val auxFile = "$fileName.aux"

	val n = nei.size
	val edges = nei.sumBy { it.size } / 2
	val r = kotlin.random.Random(566)
	println("$fileName $n ${2.0 * edges / n}")

	fun eval(color: IntArray): Double {
		val map = mutableMapOf<Int, Int>()
		for (c in color) if (c !in map) map[c] = map.size
		val parts = map.size
		val eIn = IntArray(parts)
		val eOut = IntArray(parts)
		val size = IntArray(parts)
		val sumDeg = IntArray(parts)
		for (u in nei.indices) {
			val cu = map[color[u]]!!
			size[cu]++
			sumDeg[cu] += nei[u].size
			for (v in nei[u]) {
				if (map[color[v]] == cu) eIn[cu]++ else eOut[cu]++
			}
		}
		val modularity = eIn.indices.sumByDouble { eIn[it] * 0.5 / edges - (sumDeg[it] * 0.5 / edges).pow(2) }
		val regularization = 0.5 * (eIn.indices.sumByDouble { if (size[it] == 1) 1.0 else eIn[it].toDouble() / (size[it] * (size[it] - 1)) } / parts - parts.toDouble() / n)
		//		println("$modularity $regularization $res")
		return (1 + modularity + regularization) * 1e5
	}

	fun randomCenters(parts: Int) = nei.indices.shuffled(r).take(parts).toIntArray()

	fun byCenters(centers: IntArray): IntArray {
		val inf = n + 1
		val queue = IntArray(n)
		val dist = IntArray(n) { inf }
		val which = IntArray(n) { inf }
//		val size = IntArray(n) { 0 }
		var low = 0; var high = 0
		for (c in centers) {
			dist[c] = 0
			which[c] = c
//			size[c] = 1
			queue[high++] = c
		}
		while (low < high) {
			val u = queue[low++]
			for (v in nei[u]) {
				if (dist[v] < inf) continue
				dist[v] = dist[u] + 1
				which[v] = which[u]
//				size[which[v]]++
				queue[high++] = v
			}
		}
		return which
	}

	@Suppress("unused")
	fun bestParts(): Int {
		return (10000..60000 step 1000).maxBy { parts ->
			val q = List(8) { eval(byCenters(randomCenters(parts))) }.max()!!
			println("$parts $q")
			q
		}!!
	}
	val bestParts = mapOf("a1" to 21000, "a2" to 29000, "a3" to 29000)

	val parts = bestParts[fileName] ?: error("")
//	val centers = randomCenters(parts)
	val centers = BufferedReader(FileReader(auxFile)).readLine().split(" ").map { it.toInt() }.toIntArray()
	var answer = byCenters(centers)
	var best = eval(answer)

	fun improve1() {
		val index = r.nextInt(centers.size)
		val oldValue = centers[index]
		val centersSet = centers.toMutableSet()
		centersSet.remove(oldValue)
		while (true) {
			centers[index] = r.nextInt(n)
			if (centers[index] in centersSet) continue
			centersSet.add(centers[index])
			break
		}
		val new = byCenters(centers)
		val score = eval(new)
		print("$best -> $score")
		if (score > best) {
			best = score
			answer = new
			print(" YES")
		} else {
			centersSet.remove(centers[index])
			centers[index] = oldValue
			centersSet.add(oldValue)
		}
		println()
	}

	fun improve2() {
		val old = centers.clone()
		val level = 256
		for (i in centers.indices) {
			if (r.nextInt(level) == 0) centers[i] = -1
		}
		val set = centers.toMutableSet()
		for (i in centers.indices) {
			if (centers[i] != -1) continue
			while (true) {
				centers[i] = r.nextInt(n)
				if (centers[i] in set) continue
				set.add(centers[i])
				break
			}
		}
		val new = byCenters(centers)
		val score = eval(new)
		print("$best -> $score")
		if (score > best) {
			best = score
			answer = new
			print(" YES")
		} else {
			for (i in centers.indices) centers[i] = old[i]
		}
		print(" $level")
		println()
	}

	class Centers(val a: IntArray) {
		private val energy: Double = eval(byCenters(a))
		fun energy(): Double = energy

		fun randomInstance(random: Random): Centers {
			val list = ArrayList<Int>()
			for (i in 0 until n) {
				list.add(i)
			}
			list.shuffle(random)
			for (i in 0 until parts) {
				a[i] = list[i]
			}
			return Centers(a)
		}

		fun vary(): Centers {
			val new: IntArray = a.clone()
			val level = 256
			for (i in new.indices) {
				if (r.nextInt(level) == 0) new[i] = -1
			}
			val set = new.toMutableSet()
			for (i in new.indices) {
				if (new[i] != -1) continue
				while (true) {
					new[i] = r.nextInt(n)
					if (new[i] in set) continue
					set.add(new[i])
					break
				}
			}
			return Centers(new)
		}
	}

	class Settings constructor(var globalIterations: Int = 1024, var iterations: Int = 8192, var probStartWithPrevious: Double = 1 - 1.0 / 16, var recessionLimit: Int = Int.MAX_VALUE, var desiredEnergy: Double = -Double.MAX_VALUE, var temp0: Double = 1.0)

	repeat(10) {
		improve1()
		improve2()
	}

	fun simulatedAnnealing(item: Centers, settings: Settings, r1: Random): Centers? {
		var item = item
		var energy = item!!.energy()
		var answerEnergy = Double.MAX_VALUE
		var answer: Centers? = null
		for (glob in 0 until settings.globalIterations) {
			if (glob > 0 && r1.nextDouble() >= settings.probStartWithPrevious) {
				item = item!!.randomInstance(r1)
				energy = item.energy()
			}
			var end = settings.iterations
			var iter = 1
			var recession = 0
			while (true) {
				if (energy < answerEnergy) {
					answerEnergy = energy
					answer = item
					println(answerEnergy)
					if (answerEnergy <= settings.desiredEnergy) {
						return answer
					}
					end = Math.max(end, iter + settings.iterations)
				}
				if (iter > end) {
					break
				}
				var nextEnergy: Double
				var next: Centers? = null
				next = item!!.vary()!!
				nextEnergy = next.energy()
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
					val barrier = Math.exp(-1.0 * dEnergy * iter / settings.temp0)
					accept = r1.nextDouble() < barrier
				}
				if (accept) {
					assert(next != null)
					item = next!!
					energy = nextEnergy
				} else {
				}
				iter++
			}
		}
		return answer
	}

	val settings = Settings()
	val item = Centers(centers)
	val q: Centers = simulatedAnnealing(item, settings, r.asJavaRandom()) as Centers
	for (i in centers.indices) centers[i] = q.a[i]

	val output = PrintWriter(outputFile)
	val aux = PrintWriter(auxFile)
	answer.indices.groupBy { answer[it] }.values.sortedBy { it.size }.forEach { output.println(it.joinToString(" ")) }
	aux.println(centers.joinToString(" "))
	output.close()
	aux.close()
}

fun main() {
	for (test in 1..3) {
		val fileName = "a$test"
		val input = BufferedReader(FileReader("${fileName}.in"))
		val edges = generateSequence { input.readLine()?.split(" ")?.map { it.toInt() } }.toList()
		val n = edges.flatten().max()!! + 1
		val nei = List(n) { mutableListOf<Int>() }
		for ((u, v) in edges) { nei[u].add(v); nei[v].add(u) }
		solve(fileName, nei.map { it.toIntArray() })
	}
}
