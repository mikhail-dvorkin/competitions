package marathons.atcoder.ahc23_asprova10_cropsOnGrid //TESTING

import java.io.*
import java.util.concurrent.Callable
import kotlin.math.exp
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.reflect.KFunction0
import kotlin.system.measureTimeMillis

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(0 until 100, marathons.utils.atcoder.atcoderVisualizerCallable(::solveIO)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
const val TIME_LIMIT = 2000 - 250
var timeStart = 0L
fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

fun solve(months: Int, y0: Int, wallsHor: List<String>, wallsVer: List<String>, crops: List<Crop>): List<Task> {
	var bestScore = -1
	var bestPlan: List<Task> = listOf()
	bfsAllMemo = null
	fun solveOnce() {
		val (score, plan) = solveOnce(months, y0, wallsHor, wallsVer, crops)
		if (score > bestScore) {
			bestScore = score
			bestPlan = plan
		}
	}
	for (iter in 0 until 42) {
		pCoverDist = 7 + iter % 7
		pAdhocMOdd = 9 + iter % 2 * 2
		pAdhocAllowDifference = iter % 3
		try {
			logTime(::solveOnce)
		} catch (_: TimeOutException) {
			break
		}
	}
	return bestPlan
}

var pCoverDist = 9
var pUseCenterness = 1
var pSaveForCorridor = 3
var pGroupMaxDifference = 6
var pLimitNonSingleGroup = 3
var pAdhocMOdd = 9
var pAdhocSize = 8
var pAdhocMaxLength = 8
var pAdhocAllowDifference = 1
var bfsAllMemo: List<List<Pair<List<IntArray>, List<IntArray>>>>? = null

fun solveOnce(months: Int, y0: Int, wallsHor: List<String>, wallsVer: List<String>, crops: List<Crop>): Pair<Int, List<Task>> {
	val n = wallsVer.size
	val allCells = IntArray(n * n) { encode(it / n, it % n) }
	val mark = List(n) { IntArray(n) }; var markTime = 0
	val queue = IntArray(n * n)
	val distInf = n * n + 1

	fun move(y: Int, x: Int, d: Int): Pair<Int, Int>? {
		val yy = y + DY[d]; val xx = x + DX[d]
		if (yy !in 0 until n || xx !in 0 until n) return null
		val wall = if (yy == y) wallsVer[y][minOf(x, xx)] else wallsHor[minOf(y, yy)][x]
		return if (wall == '1') null else (yy to xx)
	}
	fun moves(y: Int, x: Int) = (0 until 4).mapNotNull { move(y, x, it) }

	fun bfs(from: Collection<Int>): Pair<List<IntArray>, List<IntArray>> {
		val (dist, parent) = List(2) { List(n) { IntArray(n) } }
		dist.forEach { it.fill(distInf) }
		var low = 0; var high = 0
		for (cell in from) {
			val (y, x) = decode(cell)
			dist[y][x] = 0
			parent[y][x] = -1
			queue[high++] = cell
		}
		while (low < high) {
			val (y, x) = decode(queue[low++])
			for ((yy, xx) in moves(y, x)) {
				if (dist[yy][xx] != distInf) continue
				dist[yy][xx] = dist[y][x] + 1
				parent[yy][xx] = encode(y, x)
				queue[high++] = encode(yy, xx)
			}
		}
		return dist to parent
	}
	fun bfsAll() = List(n) { y -> List(n) { x -> bfs(listOf(encode(y, x))) } }
	if (bfsAllMemo == null) bfsAllMemo = logTime(::bfsAll)
	val bfsAll = bfsAllMemo!!
	val dist = List(n) { y -> List(n) { x -> bfsAll[y][x].first } }
	fun dist(y1: Int, x1: Int, y2: Int, x2: Int) = dist[y1][x1][y2][x2]
	fun dist(cell1: Int, cell2: Int): Int {
		val (y1, x1) = decode(cell1)
		val (y2, x2) = decode(cell2)
		return dist(y1, x1, y2, x2)
	}
	logIntArrays(dist[y0][0])

	fun centerness() = if (pUseCenterness == 1) List(n) { y -> List(n) { x ->
		var score = 0.0
		for (dy in -pCoverDist..pCoverDist) {
			val yy = y + dy
			if (yy !in 0 until n) continue
			for (dx in -pCoverDist..pCoverDist) {
				val xx = x + dx
				if (xx !in 0 until n) continue
				score += exp(-dist[y][x][yy][xx].toDouble())
			}
		}
		score
	} } else null
	val centerness = logTime(::centerness)

	fun simpleSteiner(points: List<Int>): Set<Int> {
		val network = points.toMutableSet()
		fun connect(cell1: Int, cell2: Int) {
			val (y1, x1) = decode(cell1)
			val dd = dist[y1][x1]
			val pp = bfsAll[y1][x1].second
			var (y, x) = decode(cell2)
			while (y != y1 || x != x1) {
				val (yNew, xNew) = if (pUseCenterness == 1) {
					moves(y, x)
						.filter { (yy, xx) -> dd[yy][xx] == dd[y][x] - 1 }
						.maxBy { (yy, xx) -> centerness!![yy][xx] }
				} else {
					decode(pp[y][x])
				}
				network.add(encode(yNew, xNew))
				y = yNew; x = xNew
			}
		}
		val edges = points.indices.cartesianTriangle().sortedBy { dist(points[it.first], points[it.second]) }
		val dsu = DisjointSetUnion(points.size)
		var result = 0
		for ((i, j) in edges) {
			if (dsu[i] == dsu[j]) continue
			dsu.unite(i, j)
			result += dist(points[i], points[j])
			val toConnect = mutableListOf(points[i], points[j])
			run {
				var notImproved = 0
				while (notImproved < 2) {
					markTime++
					var best = toConnect[0]
					fun dfs(here: Int) {
						val (y, x) = decode(here)
						mark[y][x] = markTime
						if (dist(here, toConnect[1]) < dist(best, toConnect[1])) best = here
						for ((yy, xx) in moves(y, x)) {
							val there = encode(yy, xx)
							if (there !in network) continue
							if (mark[yy][xx] == markTime) continue
							dfs(there)
						}
					}
					dfs(best)
					if (best != toConnect[0]) {
						toConnect[0] = best
					} else {
						notImproved++
					}
					toConnect.reverse()
				}
			}
			connect(toConnect[0], toConnect[1])
		}
		return network
	}

	fun makeKDistCover(): Triple<MutableList<Int>, Set<Int>, Pair<List<IntArray>, List<IntArray>>> {
		val k = pCoverDist
		val cover = mutableListOf(encode(y0, 0))
		while (true) {
			val network = simpleSteiner(cover)
			val bfs = bfs(network)
			var maxDist = -1; var yMaxDist = -1; var xMaxDist = -1
			for (y in 0 until n) for (x in 0 until n) {
				if (bfs.first[y][x] > maxDist) {
					maxDist = bfs.first[y][x]; yMaxDist = y; xMaxDist = x
				}
			}
			if (maxDist <= k) return Triple(cover, network, bfs)
			val furthest = encode(yMaxDist, xMaxDist)
			val potentialNew = allCells.filter {
				val (y, x) = decode(it)
				bfs.first[y][x] == maxDist - k && dist(it, furthest) == k
			}
			require(potentialNew.isNotEmpty()) //TESTING
			if (potentialNew.size == 1) {
				cover.add(potentialNew[0])
				continue
			}
			val new = potentialNew.maxBy {
				val (yNew, xNew) = decode(it)
				val dd = dist[yNew][xNew]
				var score = 0
				for (y in 0 until n) for (x in 0 until n) {
					if (bfs.first[y][x] > k && dd[y][x] <= k) score++
				}
				score
			}
			cover.add(new)
		}
	}
	val (_, corridor1, bfsCorridor1) = logTime(::makeKDistCover)
	logIntArrays(bfsCorridor1.first)

	tailrec fun improveCorridor(corridor: Set<Int>): Set<Int> {
		for (cell in corridor) {
			val (y, x) = decode(cell)
			if (y == y0 && x == 0) continue
			val cn = moves(y, x).filter { (yy, xx) -> encode(yy, xx) in corridor }
			if (cn.size != 2) continue
			val yDiagonal = cn[0].first + cn[1].first - y; val xDiagonal = cn[0].second + cn[1].second - x
			if (yDiagonal == y && xDiagonal == x) continue
			if (encode(yDiagonal, xDiagonal) in corridor && moves(yDiagonal, xDiagonal).containsAll(cn)) {
				info { "A $y $x" }
				return improveCorridor(corridor - cell)
			}
		}
		return corridor
	}
	fun iCorridor() = improveCorridor(corridor1)
	val corridor = logTime(::iCorridor)
	val bfsCorridor = bfs(corridor)

	fun makeGroups(): MutableList<MutableList<Int>> {
		val groupId = List(n) { IntArray(n) { -2 } }
		for (cell in corridor) {
			val (y, x) = decode(cell)
			groupId[y][x] = -1
		}
		val groupLeaders = mutableMapOf<Int, Int>()
		val groups = mutableListOf<MutableList<Int>>()
		for (cell in allCells) {
			val (y, x) = decode(cell)
			if (groupId[y][x] == -1) continue
			var yy = y; var xx = x
			while (true) {
				val (yNew, xNew) = decode(bfsCorridor.second[yy][xx])
				if (groupId[yNew][xNew] == -1) {
					val groupLeader = encode(yy, xx)
					groupId[y][x] = groupLeaders.getOrPut(groupLeader) {
						groups.add(mutableListOf())
						groupLeaders.size
					}
					groups[groupId[y][x]].add(cell)
					break
				}
				yy = yNew; xx = xNew
			}
		}
		return groups
	}
	val groups = logTime(::makeGroups)
	groups.sortBy { -it.size }
	fun groupId(): List<IntArray> {
		val groupId = List(n) { IntArray(n) { -1 } }
		groups.forEachIndexed { index, group -> for (cell in group) { val (y, x) = decode(cell); groupId[y][x] = index } }
		return groupId
	}
	val groupId = logTime(::groupId)
	logIntArrays(groupId)
	log { "\n" + groups.map { it.size } }

	fun okToGive(group: List<Int>): Boolean {
		val asSet = group.toSet()
		markTime++
		var seenCount = 0
		var seenCorridor = false
		fun dfs(here: Int) {
			val (y, x) = decode(here)
			mark[y][x] = markTime
			seenCount++
			for ((yy, xx) in moves(y, x)) {
				if (groupId[yy][xx] == -1) seenCorridor = true
				val there = encode(yy, xx)
				if (there !in asSet) continue
				if (mark[yy][xx] == markTime) continue
				dfs(there)
			}
		}
		dfs(group[0])
		return seenCount == asSet.size && seenCorridor
	}
	var improvedB = 0
	tailrec fun improveGroups() {
		var improved = false
		for (cellGive in allCells) {
			val (yGive, xGive) = decode(cellGive)
			val groupGive = groupId[yGive][xGive]
			if (groupGive == -1) continue
			require(cellGive in groups[groupGive]) //TESTING
			val neighborGroups = moves(yGive, xGive).map { groupId[it.first][it.second] }.toSet()
			for (groupTake in neighborGroups) {
				if (groupTake == -1 || groups[groupGive].size <= groups[groupTake].size + 1) continue
				if (!okToGive(groups[groupGive] - cellGive)) continue
				groups[groupGive].remove(cellGive)
				groups[groupTake].add(cellGive)
				groupId[yGive][xGive] = groupTake
				improved = true
				improvedB++
				break
			}
		}
		if (improved) improveGroups()
	}
	logTime(::improveGroups)
	info { "B #$improvedB" }
	logIntArrays(groupId())

	fun sortGroupsAndCorridor(): Triple<List<IntArray>, MutableList<Int>, List<IntArray>> {
		val logistic = List(n) { IntArray(n) }
		val orderedCorridor = mutableListOf<Int>()
		groups.sortBy { -it.size }
		for (group in groups) {
			val asSet = group.toSet()
			val leader = group.first { val (y, x) = decode(it); bfsCorridor.first[y][x] == 1 }
			val (yLeader, xLeader) = decode(leader)
			val entrance = moves(yLeader, xLeader)
				.first { (y, x) -> groupId[y][x] == -1 }
				.let { encode(it.first, it.second) }
			group.clear()
			markTime++
			fun dfs(here: Int) {
				val (y, x) = decode(here)
				logistic[y][x] = entrance
				mark[y][x] = markTime
				for ((yy, xx) in moves(y, x)) {
					val there = encode(yy, xx)
					if (there !in asSet) continue
					if (mark[yy][xx] == markTime) continue
					dfs(there)
				}
				group.add(here)
			}
			dfs(leader)
		}

		val child = List(n) { IntArray(n) { -1 } }
		val height = List(n) { IntArray(n) }
		markTime++
		fun dfs(y: Int, x: Int) {
			val here = encode(y, x)
			mark[y][x] = markTime
			for ((yy, xx) in moves(y, x)) {
				if (groupId[yy][xx] != -1) continue
				if (mark[yy][xx] == markTime) continue
				logistic[yy][xx] = here
				dfs(yy, xx)
				if (height[yy][xx] > height[y][x]) {
					height[y][x] = height[yy][xx]
					child[y][x] = encode(yy, xx)
				}
			}
			height[y][x]++
			orderedCorridor.add(here)
		}
		dfs(y0, 0)
		logistic[y0][0] = -1
		return Triple(logistic, orderedCorridor, child)
	}
	val (logistic, orderedCorridor, child) = logTime(::sortGroupsAndCorridor)

	var myScore = 0
	fun solveGroups(): List<Task> {
		val cropCount = List(months) { List(months) { mutableListOf<Crop>() } }
		for (crop in crops) {
			cropCount[crop.startBy][crop.end].add(crop)
		}
		fun takeIJ(i: Int, j: Int, toTake: Int, blocked: MutableSet<Int>, actually: Boolean): Pair<Int, List<Crop>?> {
			var t = toTake
			var score = 0
			val used = if (actually) mutableListOf<Crop>() else null
			val low = if (toTake == 1) 2 else pSaveForCorridor
			for (len in j - i downTo maxOf(low, j - i - pGroupMaxDifference)) {
				if (t == 0) break
				for (k in j - len downTo i) {
					if (t == 0) break
					if (k + len - 1 in blocked) break
					val such = cropCount[k][k + len - 1]
					val take = minOf(t, such.size)
					score += take * len
					t -= take
					if (actually) repeat(take) { used!!.add(such.removeLast()) }
				}
			}
			return score to used
		}

		val blocked = List(n) { List(n) { mutableSetOf<Int>() } }
		fun propagateBlocked() {
			for (cell in orderedCorridor.reversed()) {
				val (y, x) = decode(cell)
				val parent = logistic[y][x]
				if (parent == -1) continue
				val (yParent, xParent) = decode(parent)
				blocked[y][x].addAll(blocked[yParent][xParent])
			}
		}

		val plan = mutableListOf<Task>()
		val passedThrough = List(n) { List(n) { mutableSetOf<Int>() } }
		fun adhoc() {
			val intervals = (1 until pAdhocMOdd step 2).map {
				val center = ((it + 0.5) * months / pAdhocMOdd).roundToInt()
				center - pAdhocSize / 2 to center + pAdhocSize - pAdhocSize / 2
			}
//			val intervals1 = (10..80 step 14).map { it to it + 9 }
//			val intervals2 = (10..82 step 18).map { it to it + 8 }
//			val intervals3 = (13..79 step 22).map { it to it + 8 }
			val adhocSize = intervals.minOf { (i, j) ->
				(0..pAdhocAllowDifference).sumOf { cropCount[i + it][j - 1].size }
			}.coerceAtMost(pAdhocMaxLength)

			val longPath = generateSequence(encode(y0, 0)) { cell ->
				val (y, x) = decode(cell)
				child[y][x].takeIf { it != -1 }
			}.toList()
			for (cell in longPath.takeLast(adhocSize)) {
				val (y, x) = decode(cell)
				for (interval in intervals) {
					val (i, j) = interval
					val crop = (0..pAdhocAllowDifference).asSequence()
						.map { cropCount[i + it][j - 1] }
						.first { it.isNotEmpty() }
						.removeLast()
					plan.add(Task(crop.id, y, x, i))
					passedThrough[y][x].apply {
						add(i)
						add(j)
					}
					myScore += crop.end + 1 - crop.startBy
					blocked[y][x].addAll(i until j)
				}
			}
			propagateBlocked()
		}
		logTime(::adhoc)

		fun planGroups() {
			for (iter in 0..1) for (group in groups) {
				checkTimeLimit()
				val (yGroup, xGroup) = decode(group[0])
				val (yEntrance, xEntrance) = decode(logistic[yGroup][xGroup])
				val blockedHere = blocked[yEntrance][xEntrance]
				if (blockedHere.isEmpty() == (iter == 0)) continue

				val (dp, dpHow) = List(2) { IntArray(months + 1) { -2 } }
				dp[0] = 0
				for (j in 1..months) {
					dp[j] = dp[j - 1]
					dpHow[j] = j - 1
					val minI = if (group.size > 1) maxOf(j - months / pLimitNonSingleGroup, 0) else 0
					for (i in minI..j - 2) {
						if (cropCount[i][j - 1].isEmpty()) continue
						if (i in blockedHere) continue
						val scoreIJ = dp[i] + takeIJ(i, j, group.size, blockedHere, false).first
						if (scoreIJ > dp[j]) {
							dp[j] = scoreIJ
							dpHow[j] = i
						}
					}
				}
				var j = months
				myScore += dp[j]
				log { group.size to (dp[j] * 1e5 / group.size).roundToInt() }
				while (j > 0) {
					val i = dpHow[j]
					if (i == j - 1) { j = i; continue }
					val usedCrops = takeIJ(i, j, group.size, blockedHere, true).second!!.sortedBy { -it.end }
					var ii = i
					while (dpHow[ii] == ii - 1 && (ii - 1) !in blockedHere) ii--
					log { "$ii ${i..j}" }
					require(ii !in blockedHere) //TESTING
					for (k in usedCrops.indices) {
						val (y, x) = decode(group[k])
						val cropId = usedCrops[k].id
						plan.add(Task(cropId, y, x, ii))
						passedThrough[yEntrance][xEntrance].apply {
							add(ii)
							add(crops[cropId].end + 1)
						}
					}
					j = ii
				}
			}
		}
		logTime(::planGroups)
//		log { "Left for corridor:" }; for (row in cropCount) for (cell in row) for (crop in cell) log { "$crop" }

		var improvedC = 0
		fun planCorridor(strictness: Int) {
			for (cell in orderedCorridor) {
				val (y, x) = decode(cell)
				val parent = logistic[y][x]
				val passed = passedThrough[y][x].sorted()
				if (passed.isEmpty()) {
					info { "WEIRD $y,$x" }
					continue
				}
				if (strictness == 1) {
					log { "$y $x passed at $passed" }
					log { "blocked ${blocked[y][x].sorted()}" }
				}
				passed.zipWithNext { i, j ->
					for (len in j - i downTo maxOf(j - i - strictness, 2)) {
						for (k in j - len downTo i) {
							if (i in blocked[y][x]) continue
							if (k + len in blocked[y][x]) continue
							val crop = cropCount[k][k + len - 1].removeLastOrNull()
								?: continue
							improvedC++
							val path = mutableListOf(cell)
							while (true/*path.size < 2*/) {
								val (yLast, xLast) = decode(path.last())
								val cellChild = child[yLast][xLast]
								if (cellChild == -1) break
								val (yChild, xChild) = decode(cellChild)
								if ((i..crop.end).any { it in blocked[yChild][xChild] }) break
								path.add(cellChild)
							}
//							if (path.size >= 3) info { "child${path.size}" }
							val (yLast, xLast) = decode(path.last())
							plan.add(Task(crop.id, yLast, xLast, i))
							myScore += len
							blocked[yLast][xLast].addAll(i..crop.end)
							for (cc in path) {
								val (yy, xx) = decode(cc)
								passedThrough[yy][xx].apply {
									add(i)
									add(crop.end + 1)
								}
							}
							return@zipWithNext
						}
					}
				}
				if (parent != -1) {
					val (yParent, xParent) = decode(parent)
					passedThrough[yParent][xParent].addAll(passedThrough[y][x])
				}
			}
			info { "C$strictness #$improvedC" }
			propagateBlocked()
		}

		fun improveC() {
			for (iter in 0..months) {
				improvedC = 0
				planCorridor(iter)
				if (improvedC == 0) break
			}
		}
		logTime(::improveC)
//		log { "Left after improveC:" }; for (row in cropCount) for (cell in row) for (crop in cell) log { "$crop" }

		return plan
	}

	val plan = solveGroups()
//	val plan = debugPlan(cover - encode(y0, 0))
	info { "\$${myScore * 25}" }
	return myScore to plan
}

private fun <T> logTime(function: KFunction0<T>): T {
	checkTimeLimit()
	if (!VERBOSE) return function()
	val result: T
	val time = measureTimeMillis { result = function() }
	log { "_time ${function.name} $time" }
	if (time > 200) info { "${function.name}:$time" }
	return result
}

private fun debugPlan(toPlan: List<Int>) = toPlan.mapIndexed { index, point ->
	val (y, x) = decode(point)
	Task(index, y, x, 0)
}

data class Task(val cropId: Int, val y: Int, val x: Int, val start: Int)
data class Crop(val id: Int, val startBy: Int, val end: Int)
val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

private fun <T> Iterable<T>.cartesianTriangle() = withIndex().flatMap { x -> take(x.index).map { it to x.value } }
fun encode(y: Int, x: Int) = (y shl 16) or x
fun decode(code: Int) = (code shr 16) to (code.toShort().toInt())

class DisjointSetUnion(n: Int) {
	val p = IntArray(n)
	val r = Random(566)

	init { clear() }

	fun clear() { for (i in p.indices) p[i] = i }

	operator fun get(v: Int): Int {
		if (p[v] == v) return v
		p[v] = get(p[v])
		return p[v]
	}

	fun unite(v: Int, u: Int) {
		val vv = get(v); val uu = get(u)
		if (r.nextBoolean()) p[vv] = uu else p[uu] = vv
	}
}

class TimeOutException : RuntimeException()

fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()
	fun readLn() = `in`.readLine()!!
	fun readInt() = readLn().toInt()
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }

	val (months, hei, _, y0) = readInts()
	val wallsHor = List(hei - 1) { readLn() }
	val wallsVer = List(hei) { readLn() }
	val crops = List(readInt()) { val (s, e) = readInts(); Crop(it, s - 1, e - 1) }
	val plan = solve(months, y0, wallsHor, wallsVer, crops)
	out.println(plan.size)
	for (task in plan) out.println("${task.cropId + 1} ${task.y} ${task.x} ${task.start + 1}")
	out.close()
	log?.close()
	return null
}

private inline fun log(msg: () -> Any) { log?.println(msg()) }
private inline fun info(msg: () -> Any) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }
fun logIntArrays(table: List<IntArray>) = log { "\n" + table.joinToString("\n") { it.joinToString("\t") } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
