package marathons.atcoder.ahc43_nihonbashi2025winter_railwayCompany //TESTING

import java.io.BufferedReader
import java.io.File
import java.io.PrintWriter
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

private val TO_EVAL = (0 until 10)//.let { listOf(3) }
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (3_000 - 150)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

private fun solve(n: Int, people: List<List<Int>>, moneyAtStart: Int, days: Int, toVisualize: MutableList<Any>?): List<List<Int>> {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()

	val nn = n * n
	val s = 2
	val costStation = 5000
	val costRail = 100

	val magicStartRichness = 5 * costStation

	val cellId = List(n) { IntArray(n) }
	val cellY = IntArray(nn)
	val cellX = IntArray(nn)
	val cells = 0 until nn
	for (y in 0 until n) for (x in 0 until n) {
		val id = y * n + x
		cellId[y][x] = id
		cellY[id] = y
		cellX[id] = x
	}
	fun outside(y: Int, x: Int) = (y !in 0 until n) || (x !in 0 until n)
	fun dist(c1: Int, c2: Int) = abs(cellY[c1] - cellY[c2]) + abs(cellX[c1] - cellX[c2])
//	fun distLInf(c1: Int, c2: Int) = maxOf(abs(cellY[c1] - cellY[c2]), abs(cellX[c1] - cellX[c2]))
	fun distFromCenterLinear(y: Int) = if (y >= n / 2) (y - n / 2) else (n / 2 - 1 - y)
	val distFromCenter = IntArray(nn) { distFromCenterLinear(cellY[it]) + distFromCenterLinear(cellX[it]) }

	val connected = List(nn) { mutableListOf<Int>() }
	for (person in people) {
		val v = cellId[person[0]][person[1]]
		val u = cellId[person[2]][person[3]]
		connected[u].add(v)
		connected[v].add(u)
	}
	val populated = BooleanArray(nn) { connected[it].isNotEmpty() }

	fun stationsProject(mustBuild: List<Int>): List<Int> {
		val covered = BooleanArray(nn)
		val nearStation = BooleanArray(nn)
		val stations = mutableListOf<Int>()
		fun addStation(station: Int) {
			val sy = cellY[station]
			val sx = cellX[station]
			require(!nearStation[station])
			stations.add(station)
			for (dy in -s..s) for (dx in -s..s) {
				if (abs(dy) + abs(dx) > s) continue
				val y = sy + dy
				val x = sx + dx
				if (outside(y, x)) continue
				val c = cellId[y][x]
				covered[c] = true
				if (abs(dy) <= 1 && abs(dx) <= 1) nearStation[c] = true
			}
		}
		for (station in mustBuild) addStation(station)
		while (true) {
			val toCoverList = timed {
				cells.filter { populated[it] && !covered[it] }
			}
			if (toCoverList.isEmpty()) break
			val toCover = toCoverList.maxBy { distFromCenter[it] }
			var bestSY = -1; var bestSX = -1; var bestCover = 0
			for (dy in -s..s) for (dx in -s..s) {
				if (abs(dy) + abs(dx) > s) continue
				val sy = cellY[toCover] + dy
				val sx = cellX[toCover] + dx
				if (outside(sy, sx)) continue
				val sc = cellId[sy][sx]
				if (nearStation[sc]) continue
				var cover = 0
				for (ey in -s..s) for (ex in -s..s) {
					if (abs(dy) + abs(dx) > s) continue
					val y = sy + ey
					val x = sx + ex
					if (outside(y, x)) continue
					val c = cellId[y][x]
					if (populated[c] && !covered[c]) cover++
				}
				cover = cover * nn - distFromCenter[cellId[sy][sx]]
				if (cover > bestCover) {
					bestCover = cover
					bestSY = sy
					bestSX = sx
				}
			}
			addStation(cellId[bestSY][bestSX])
		}
		return stations
	}

	fun evalFirstPair(s1: Int, s2: Int): Int {
		val costOfBuilding = 2 * costStation + (dist(s1, s2) - 1) * costRail * 3 / 2
		if (costOfBuilding > moneyAtStart) return Int.MAX_VALUE
		val timeOfBuilding = dist(s1, s2) + 1
		var dailyIncome = 0
		for (dy in -s..s) for (dx in -s..s) {
			if (abs(dy) + abs(dx) > s) continue
			val y = cellY[s1] + dy; val x = cellX[s1] + dx
			if (outside(y, x)) continue
			val c = cellId[y][x]
			for (d in connected[c]) {
				if (dist(d, s2) <= s) dailyIncome += dist(c, d)
			}
		}
		if (dailyIncome == 0) return Int.MAX_VALUE
		val timeToRichness = timeOfBuilding + (magicStartRichness - (moneyAtStart - costOfBuilding)) / dailyIncome
		return timeToRichness
	}

	fun evalStationsFromScratch() {
		val stationsProject = stationsProject(listOf())
		var bestEval = days
		for (s in stationsProject) for (t in stationsProject) if (s < t) {
			val eval = evalFirstPair(s, t)
			if (eval >= days) continue
			bestEval = minOf(bestEval, eval)
		}
		info { "Best in stations: $bestEval" }
	}
//	evalStationsFromScratch()

	fun bestFirstPair(): Pair<Int, Int> {
		var bestEvalAll = Int.MAX_VALUE
		var bestPair = -1 to -1
		val pairs = mutableSetOf<Pair<Int, Int>>()
		for (v in cells) for (u in connected[v]) if (v < u) {
			for (dy in -s..s) for (dx in -s..s) {
				if (abs(dy) + abs(dx) > s) continue
				val yv = cellY[v] + dy;
				val xv = cellX[v] + dx
				if (outside(yv, xv)) continue
				val c = cellId[yv][xv]
				for (ey in -s..s) for (ex in -s..s) {
					if (abs(ey) + abs(ex) > s) continue
					val yu = cellY[u] + ey;
					val xu = cellX[u] + ex
					if (outside(yu, xu)) continue
					val d = cellId[yu][xu]
					pairs.add(minOf(c, d) to maxOf(c, d))
				}
			}
		}
		for ((s, t) in pairs) {
			val eval = evalFirstPair(s, t)
			if (eval < bestEvalAll) {
				bestEvalAll = eval
				bestPair = s to t
			}
			bestEvalAll = minOf(bestEvalAll, eval)
		}
		info { "Best in all: $bestEvalAll" }
		return bestPair
	}

	fun mst(stations: List<Int>): List<List<Int>> {
		fun selectDirs(v: Int, neiV: List<Int>): IntArray? {
			val cv = stations[v]
			val e = List(4) { BooleanArray(neiV.size) }
			for (i in neiV.indices) {
				val u = neiV[i]
				val cu = stations[u]
				val dy = cellY[cu] - cellY[cv]
				val dx = cellX[cu] - cellX[cv]
				for (d in DY.indices) {
					if (abs(dy) + abs(dx) > abs(dy - DY[d]) + abs(dx - DX[d])) {
						if ((dy == 0 || dy != DY[d]) && (dx == 0 || dx != DX[d])) {
							e[d][i] = true
						}
					}
				}
//				val dirs = DY.indices.filter { d ->
//					abs(dy) + abs(dx) > abs(dy - DY[d]) + abs(dx - DX[d])
//				}
//				println(dirs)
			}
//			println(e.map { it.contentToString() })

			val mark = BooleanArray(e.size)
			val left = IntArray(e[0].size) { -1 }
			fun dfs(i: Int): Boolean {
				if (mark[i]) {
					return false
				}
				mark[i] = true
				for (j in e[i].indices) {
					if (!e[i][j]) {
						continue
					}
					if (left[j] == -1 || dfs(left[j])) {
						left[j] = i
						return true
					}
				}
				return false
			}
			var ans = 0
			for (i in e.indices) {
				Arrays.fill(mark, false)
				if (dfs(i)) {
					ans++
				}
			}
//			println(ans)
			if (ans < neiV.size) {
//				info { "OPPA matching selectDirs" }
				return null
			}
			for (i in left.indices) for (j in 0 until i) {
				if (e[left[i]][j] && e[left[j]][i]) {
					info { "OPPA4" }
					val cu = stations[neiV[i]]
					val cw = stations[neiV[j]]
					val dyu = cellY[cu] - cellY[cv]
					val dxu = cellX[cu] - cellX[cv]
					val dyw = cellY[cw] - cellY[cv]
					val dxw = cellX[cw] - cellX[cv]
					val vp1 = dyu * dxw - dyw * dxu
					val vp2 = DY[left[i]] * DX[left[j]] - DY[left[j]] * DX[left[i]]
					if (vp1 * vp2 < 0) {
						val t = left[i]; left[i] = left[j]; left[j] = t
					}
				}
			}
			return left
		}

		val edges = stations.cartesianTriangle()
			.map { Triple(it.first, it.second, dist(it.first, it.second)) }
			.sortedBy { it.third }
		val indexOf = IntArray(nn) { -1 }
		for (i in stations.indices) indexOf[stations[i]] = i
		val dsu = DisjointSetUnion(stations.size)
		val e = List(stations.size) { IntArray(stations.size) { nn } }
		for (i in e.indices) e[i][i] = 0
		val nei = List(stations.size) { mutableListOf<Int>() }
		timed { for (edge in edges) {
			val v = indexOf[edge.first]
			val u = indexOf[edge.second]
			if (dsu.get(v) == dsu.get(u)) continue
			if (selectDirs(v, nei[v] + u) == null) continue
			if (selectDirs(u, nei[u] + v) == null) continue
			//if (nei[v].size == 3 || nei[u].size == 3) continue
			dsu.unite(v, u)
			e[v][u] = edge.third
			e[u][v] = edge.third
			nei[v].add(u)
			nei[u].add(v)
		} }
		timed {
			for (k in stations.indices) for (i in stations.indices) for (j in stations.indices) {
				if (e[i][j] > e[i][k] + e[k][j]) e[i][j] = e[i][k] + e[k][j]
			}
		}
		val dirs = timed { List(stations.size) { v -> selectDirs(v, nei[v])} }
//		println(dirs.map { it.contentToString() })

		fun evalFirstPair(s1: Int, s2: Int): Int {
			val costOfBuilding = 2 * costStation + (e[indexOf[s1]][indexOf[s2]]) * costRail
			if (costOfBuilding > moneyAtStart) return Int.MAX_VALUE
			val timeOfBuilding = /*dist(s1, s2)*/ e[indexOf[s1]][indexOf[s2]] + 1
			var dailyIncome = 0
			for (dy in -s..s) for (dx in -s..s) {
				if (abs(dy) + abs(dx) > s) continue
				val y = cellY[s1] + dy; val x = cellX[s1] + dx
				if (outside(y, x)) continue
				val c = cellId[y][x]
				for (d in connected[c]) {
					if (dist(d, s2) <= s) dailyIncome += dist(c, d)
				}
			}
			if (dailyIncome == 0) return Int.MAX_VALUE
			val timeToRichness = timeOfBuilding + (magicStartRichness - (moneyAtStart - costOfBuilding)) / dailyIncome
			return timeToRichness
		}

		val firstPair = timed { stations.indices.cartesianTriangle()
			.minBy { evalFirstPair(stations[it.first], stations[it.second]) } }
		val root = firstPair.first
		info { "Best in mst: ${evalFirstPair(stations[firstPair.first], stations[firstPair.second])}" }
//		debugAns.add(listOf(2, cellY[firstPair.first], cellX[firstPair.first]))
//		debugAns.add(listOf(2, cellY[firstPair.second], cellX[firstPair.second]))

		val rails = List(n) { IntArray(n) { -1 } }
		val builtStation = BooleanArray(stations.size)
		val inPrimTree = BooleanArray(stations.size)
		val covered = BooleanArray(nn)
		val ans = mutableListOf<List<Int>>()
		var money = moneyAtStart
		var income = 0
		val moneys = mutableListOf(moneyAtStart)
		fun day(action: List<Int>) {
			if (action[0] == -1 && action.size > 1) {
				info { "OPPA1" }
				return
			}
			val costNow = if (action[0] == 0) costStation else if (action[0] > 0) costRail else 0
			if (costNow > 0) {
				if (action[0] > 0 && rails[action[1]][action[2]] > 0) {
					info { "OPPA2 $action" }
					return
				}
				rails[action[1]][action[2]] = action[0]
			}
			while (money < costNow) day(listOf(-1))
			ans.add(action)
			money += income - costNow
			moneys.add(money)
		}

		fun connectNei(v: Int, u: Int) {
			require(u in nei[v])
			val dv = dirs[v]!![nei[v].indexOf(u)]
			val du = dirs[u]!![nei[u].indexOf(v)]
			val cv = stations[v]
			val cu = stations[u]
			val railway = railway(cellY[cv], cellX[cv], dv, cellY[cu], cellX[cu], du)
			for (r in railway) {
				day(r)
			}
		}
		fun station(v: Int) {
			while (money < costStation) day(listOf(-1))
			val cv = stations[v]
			val yv = cellY[cv]
			val xv = cellX[cv]
			rails[yv][xv] = 0
			builtStation[v] = true
			for (dy in -s..s) for (dx in -s..s) {
				if (abs(dy) + abs(dx) > s) continue
				val y = yv + dy; val x = xv + dx
				if (outside(y, x)) continue
				val c = cellId[y][x]
				if (!covered[c]) {
					for (d in connected[c]) {
						if (covered[d]) {
							income += dist(c, d)
						}
					}
				}
				covered[c] = true
			}
			inPrimTree[v] = true
			day(listOf(0, yv, xv))
		}
		fun prestation(v: Int) {
			val cv = stations[v]
			val yv = cellY[cv]
			val xv = cellX[cv]
			var mask = 0
			for (d in DY.indices) {
				val y = yv + DY[d]
				val x = xv + DX[d]
				if (outside(y, x)) continue
				if (rails[y][x] != -1) {
					//if ((RAILS[rails[y][x]] shr (d xor 2)) % 2 == 1)
					mask += 1 shl d
				}
			}
			if (mask.countOneBits() != 2) {
				info { "OPPA3" }
			}
			val rail = RAILS.indexOf(mask)
			day(listOf(rail, yv, xv))
			inPrimTree[v] = true
		}

		fun path(v: Int, u: Int) = buildList {
			var w = v
			while (w != u) {
				add(w)
				w = nei[w].minBy { e[it][u] }
			}
			add(u)
		}

		fun justFirstPair() {
			val firstPath = path(firstPair.first, firstPair.second)
			for (i in 1 until firstPath.size) {
				connectNei(firstPath[i - 1], firstPath[i])
			}
			for (i in 1..firstPath.size - 2) {
				prestation(firstPath[i])
			}
			station(firstPath[0])
			station(firstPath.last())
		}

		fun evalPrim(v: Int): Int {
			if (builtStation[v] || nei[v].isEmpty()) return Int.MAX_VALUE
			val path = timed { path(v, root) }
			val u = path.first { inPrimTree[it] }
			val newStations = buildSet {
				if (!builtStation[u]) add(u)
				add(v)
			}
			val newCovered = mutableSetOf<Int>()
			timed { for (w in newStations) {
				val cw = stations[w]
				val yw = cellY[cw]
				val xw = cellX[cw]
				for (dy in -s..s) for (dx in -s..s) {
					if (abs(dy) + abs(dx) > s) continue
					val y = yw + dy; val x = xw + dx
					if (outside(y, x)) continue
					val c = cellId[y][x]
					if (!covered[c]) newCovered.add(c)
				}
			} }

			val costOfBuilding = newStations.size * costStation + e[v][u] * costRail
			if (income == 0 && costOfBuilding > moneyAtStart - costStation) return Int.MAX_VALUE
			val timeToAfford = if (costOfBuilding > money) (costOfBuilding - money) / income else 0
			val timeOfBuilding = maxOf(e[v][u] + 1, timeToAfford)

			var dailyIncome = 0
			timed { for (c in newCovered) {
				for (d in connected[c]) {
					if (covered[d] || (d in newCovered && d < c)) dailyIncome += dist(c, d)
				}
			} }
			if (dailyIncome == 0 || ans.size + timeOfBuilding >= days) return Int.MAX_VALUE
			val moneyWithoutIt = money + income * (days - ans.size)
			val moneyWithIt = money + income * timeOfBuilding + (income + dailyIncome) * (days - ans.size - timeOfBuilding) - costOfBuilding
			if (moneyWithIt <= moneyWithoutIt) return Int.MAX_VALUE
			val timeToRichness = timeOfBuilding + (magicStartRichness + costOfBuilding) / dailyIncome
			return timeToRichness
		}

		fun connectPrim(v: Int) {
			val pathToRoot = path(v, root)
			val u = pathToRoot.first { inPrimTree[it] }
			val newStations = buildSet {
				if (!builtStation[u]) add(u)
				add(v)
			}
			val path = pathToRoot.takeWhile { it != u } + u
			for (i in 1 until path.size) {
				connectNei(path[i - 1], path[i])
			}
			for (i in 1..path.size - 2) {
				prestation(path[i])
			}
			for (w in newStations) station(w)
		}

		station(root)
		timed { for (iter in 0..days) {
			var vBest = -1; var vBestEval = Int.MAX_VALUE
			for (v in stations.indices) {
				val vEval = timed { evalPrim(v) }
				if (vEval < vBestEval) {
					vBest = v; vBestEval = vEval
				}
			}
			if (vBestEval == Int.MAX_VALUE) break
			timed { connectPrim(vBest) }
		} }
		while (ans.size < days) day(listOf(-1))
		info { "$money$ ++$income" }
		log { moneys }
		return ans.take(days)
	}

	val stationsProject = timed { stationsProject(bestFirstPair().toList()) }
	val ans1 = timed { mst(stationsProject) }
	val stations1 = ans1.filter { it[0] == 0 }.map { cellId[it[1]][it[2]] }
	val ans2 = timed { mst(stations1) }
	val stations2 = ans2.filter { it[0] == 0 }.map { cellId[it[1]][it[2]] }
	val ans = mst(stations2)
//	val stations3 = ans3.filter { it[0] == 0 }.map { cellId[it[1]][it[2]] }
//	val ans = mst(stations3)

	val debugStations = stations1
	val ansDebugStations = listOf<List<Int>>() +
				debugStations.map { listOf(1, cellY[it], cellX[it]) } +
//				ans +
				List(days
//						- ans.size
						- debugStations.size
				) { listOf(-1) }
//	val ans = List(days) { listOf(-1) }
//	try {
//		checkTimeLimit()
//	} catch (_: TimeOutException) {
//	}
	log?.close()
	return ans//DebugStations
}

val DY = intArrayOf(1, 0, -1, 0)
val DX = intArrayOf(0, 1, 0, -1)
val RAILS = listOf(15, 10, 5, 9, 12, 6, 3)

fun dirTo(y: Int, x: Int) = DY.indices.maxBy { d -> DY[d] * y + DX[d] * x }

fun railway(y1: Int, x1: Int, y2: Int, x2: Int): List<Pair<Int, Int>> {
	val dist = abs(y1 - y2) + abs(x1 - x2)
	if (dist == 0) return listOf(y1 to x1)
	if (dist == 1) return listOf(y1 to x1, y2 to x2)
	if (dist == 2 && y1 != y2 && x1 != x2) return listOf(y1 to x1, y1 to x2, y2 to x2)
	val yc = (y1 + y2) / 2
	val xc = (x1 + x2) / 2
	return railway(y1, x1, yc, xc) + railway(yc, xc, y2, x2).drop(1)
}

fun railway(y1: Int, x1: Int, d1: Int, y2: Int, x2: Int, d2: Int): List<List<Int>> {
	val twoSteps = abs(y1 + 2 * DY[d1] - y2 - 2 * DY[d2]) + abs(x1 + 2 * DX[d1] - x2 - 2 * DX[d2]) == abs(y1 - y2) + abs(x1 - x2) - 4
//	val twoSteps = abs(y1 - y2) + abs(x1 - x2) >= 4 && (abs(y1 - y2) >= 4 || abs(x1 - x2) >= 4)
	val railway = if (twoSteps) {
		listOf(y1 to x1, y1 + DY[d1] to x1 + DX[d1]) +
				railway(y1 + 2 * DY[d1], x1 + 2 * DX[d1], y2 + 2 * DY[d2], x2 + 2 * DX[d2]) +
				listOf(y2 + DY[d2] to x2 + DX[d2], y2 to x2)
	} else {
		listOf(y1 to x1) +
				railway(y1 + DY[d1], x1 + DX[d1], y2 + DY[d2], x2 + DX[d2]) +
				listOf(y2 to x2)
	}
	val result = mutableListOf<List<Int>>()
	for (i in 1..railway.size - 2) {
		val d3 = dirTo(railway[i - 1].first - railway[i].first, railway[i - 1].second - railway[i].second)
		val d4 = dirTo(railway[i + 1].first - railway[i].first, railway[i + 1].second - railway[i].second)
		val rail = RAILS.indexOf((1 shl d3) or (1 shl d4))
		if (rail == -1) {
			info { "OPPA3" }
		}
		result.add(listOf(rail, railway[i].first, railway[i].second))
	}
	return result
}

private fun <T> Iterable<T>.cartesianTriangle() = withIndex().flatMap { x -> take(x.index).map { it to x.value } }

class DisjointSetUnion(n: Int) {
	var p: IntArray = IntArray(n)
	var r: Random = Random(566)

	init {
		clear()
	}

	fun clear() {
		for (i in p.indices) {
			p[i] = i
		}
	}

	fun get(v: Int): Int {
		if (p[v] == v) {
			return v
		}
		p[v] = get(p[v])
		return p[v]
	}

	fun unite(v: Int, u: Int) {
		var v = v
		var u = u
		v = get(v)
		u = get(u)
		if (r.nextBoolean()) {
			p[v] = u
		} else {
			p[u] = v
		}
	}
}


private class TimeOutException : RuntimeException()

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()
	val (n, peopleCount, moneyAtStart, days) = `in`.readInts()
	val people = List(peopleCount) { `in`.readInts() }
	val answer = solve(n, people, moneyAtStart, days, toVisualize)
	out.println(answer.joinToString("\n") { it.joinToString(" ") })
	out.close()
	return toVisualize
}

fun <T> timed(f: () -> T): T {
//	return f() //SUBMISSION
	return marathons.utils.statRunTimed(f) //TESTING
}
private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
/*
38058   2295634 1,286,866,983   734th
69000   3,572,043,237   533th
978734  7,703,123,323   471th
978734  7,518,537,593   476th //597975
1006645
1340070
1350449 //2145350
 */
