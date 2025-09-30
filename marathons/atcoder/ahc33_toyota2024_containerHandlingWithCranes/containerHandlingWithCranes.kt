package marathons.atcoder.ahc33_toyota2024_containerHandlingWithCranes //TESTING

import java.io.*
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

const val USE_MY_EVAL = true
private val TO_DEBUG: Pair<Int, Int>? = null//.let { 1 to 1 }
private val TO_EVAL = if (TO_DEBUG == null) (0 until 1000) else listOf(TO_DEBUG.first)
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO), TO_EVAL, processor) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
val troubles = mutableListOf<String>()
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (3_000 - 250)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

const val n = 5
const val code = 100
const val maxReserveLimit = 8
var random = Random(0)

fun solve(incoming: List<IntArray>): List<StringBuilder> {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()
	var answerLength = 100
	var answer: List<StringBuilder>? = null

	try {
		maxReserve = 1
		for (iteration in 0 until 1024) {
			random = Random(iteration.toLong())
			checkTimeLimit()
			val strategy = strategy(incoming)
			val depends = dependencies(incoming, strategy)
			val candidate = tactic(incoming, strategy, depends, answerLength) ?: continue
			val length = candidate[0].length
			info { iteration to length }
			if (length < answerLength) {
				answer = candidate; answerLength = length
			}
		}
	} catch (_: TimeOutException) {
	}
	log?.close()
	return answer!!
}

fun tactic(incoming: List<IntArray>, strategy: ShortArray, depends: List<MutableList<Int>>, answerLength: Int): List<StringBuilder>? {
	val whichRow = IntArray(n * n)
	for (i in 0 until n) for (x in incoming[i]) whichRow[x] = i
	val whereInReserve = IntArray(n * n) { -1 }
	val released = BooleanArray(n * n)
	val occupiedByReserve = BooleanArray(n * n)
	val wantedReserve = BooleanArray(n * n)
	val occupiedLeftPosition = BooleanArray(n) { true }
	val location = IntArray(n) { locationStart(it) }
	val holding = BooleanArray(n)
	val doing = IntArray(n) { -1 }
	val doingWhat = IntArray(n) { -1 }
	val doingSpeculative = BooleanArray(n)
	val shouldBeFreeTime = IntArray(n) { -1 }
	val shouldBeFreeLocation = IntArray(n) { -1 }
	val beingDone = BooleanArray(strategy.size)
	val beingHeld = BooleanArray(strategy.size)
	val done = BooleanArray(strategy.size)

	fun workers() = 0 until n
	fun isTheRoad(location: Int): Boolean {
		val y = locationY(location); val x = locationX(location)
		return x == 1 || x == 4 || y == 2
	}
	val theReserve = listOf(location(0, 2), location(0, 3), location(1, 2), location(1, 3), location(3, 2), location(3, 3), location(4, 2), location(4, 3))
	fun distOnTheRoad(location1: Int, location2: Int): Int {
		val y1 = locationY(location1); val x1 = locationX(location1)
		val y2 = locationY(location2); val x2 = locationX(location2)
		if (x1 == x2) return abs(y1 - y2)
		return abs(y1 - 2) + abs(y2 - 2) + abs(x1 - x2)
	}
	fun distLoaded(location1: Int, location2: Int): Int {
		val d = dist(location1, location2)
		if (d <= 1) return d
		val x1 = locationX(location1)
		val y1 = locationY(location1)
		val x2 = locationX(location2)
		if (x2 == n - 1) {
			var clear = true
			for (x in x1 + 1 until x2) {
				val where = location(y1, x)
				if (occupiedByReserve[where] || wantedReserve[where]) clear = false
				if (workers().any { holding[it] && location[it] == where }) clear = false
			}
			if (clear) return d
		}
		val (r1, r2) = listOf(location1, location2).map { location -> nei(location).plus(location).filter { isTheRoad(it) } }
		var result = n * n
		for (v1 in r1) for (v2 in r2) result = minOf(result, dist(location1, v1) + distOnTheRoad(v1, v2) + dist(v2, location2))
//		log { "dl($location1, $location2)=$result" }
		return result
	}
	fun distUnloaded(location1: Int, location2: Int): Int {
		return dist(location1, location2)
	}
	fun possibleMoves(w: Int): List<Int> {
		val v = location[w]
		val all = nei(v).plus(v)
		return if (!holding[w]) all else all.filterNot { u -> (u != v && locationX(u) == 0 && occupiedLeftPosition[locationY(u)]) || occupiedByReserve[u] }
	}

	fun jobStartLocation(job: Int): Int {
		val x = strategy[job] % code
		return if (strategy[job] < 2 * code) locationStart(whichRow[x]) else whereInReserve[x].also { require(it != -1) }
	}
	fun canBeStarted(thisJob: Int): Int {
		if (done[thisJob] || beingDone[thisJob]) return infty
		var penalty = 0
		for (mask in depends[thisJob]) {
			val job = mask / 4
			if (done[job]) continue
			val myFinish = mask.hasBit(1)
			val theirFinish = mask.hasBit(0)
			if (!myFinish && !theirFinish) if (beingHeld[job]) continue
//			if (!myFinish && theirFinish) if (beingHeld[job]) continue
			if (myFinish && theirFinish) {
				if (beingHeld[job]) {
					val w = workers().first { doing[it] == job }
					val eta = shouldBeFreeTime[w]
					val thisJobContent = strategy[thisJob]
					val finalLocation = locationFinish(thisJobContent % code / n)
					val distToFinal = distLoaded(jobStartLocation(thisJob), finalLocation)
					penalty = if (distToFinal > -eta) n * n + eta - distToFinal else infty
//					penalty = infty
					continue
				}
			}
			return infty
		}
		return penalty
	}
	fun assignJob(w: Int, job: Int) {
		doing[w] = job
		beingDone[job] = true
		val jobContent = strategy[job]
		val jobStartLocation = jobStartLocation(job)
		doingWhat[w] = jobContent % code
		val finalLocation = locationFinish(jobContent % code / n)
		if (jobContent / code == 1) {
			val locationReserve = theReserve.filter { !wantedReserve[it] }.minBy {
				distLoaded(jobStartLocation, it) + distLoaded(it, finalLocation) + locationX(it) +
						random.nextInt(n)
			}
			wantedReserve[locationReserve] = true
			whereInReserve[doingWhat[w]] = locationReserve
			shouldBeFreeLocation[w] = locationReserve
		} else {
			shouldBeFreeLocation[w] = finalLocation
		}
		shouldBeFreeTime[w] = distUnloaded(location[w], jobStartLocation) + distLoaded(jobStartLocation, shouldBeFreeLocation[w]) + 2
	}

	var win = false
	fun logDoing() = doing.map { strategy.getOrElse(it) { -1 } }
	fun assignForDay(): Boolean {
		val workers = workers().toList()
		if (workers.any { doing[it] == -1 }) {
			val canBeStarted = strategy.indices.associateWith { canBeStarted(it) }.toList().filter { it.second != infty }
//			val (cReserve, cNotReserve) = canBeStartedAll.partition { strategy[it] / code == 1 }
//			val canBeStarted = cNotReserve + cReserve.take(theReserve.count { !wantedReserve[it] })

			if (canBeStarted.isEmpty() && doing.all { it == -1 }) {
				win = true
				return true
			}
			log { "canBeStarted" + canBeStarted.map { strategy[it.first] } }
			log { "jobStartLocations" + canBeStarted.map { jobStartLocation(it.first) } }
			val matrix = List(workers.size) { w -> IntArray(canBeStarted.size) { j ->
				val jobStartLocation = jobStartLocation(canBeStarted[j].first)
				if (doing[w] == -1) {
					distUnloaded(location[w], jobStartLocation)
				} else {
					val timeOfStart = shouldBeFreeTime[w]
					require(timeOfStart > 0)
					timeOfStart + distUnloaded(shouldBeFreeLocation[w], jobStartLocation) + canBeStarted[j].second +
							random.nextInt(n)
				}
			} }
			val h = hungarian(matrix)
			log { "h" + h.contentToString() }
			for (w in workers) if (doing[w] == -1 && h[w] >= 0) {
				assignJob(w, canBeStarted[h[w]].first)
				if (canBeStarted[h[w]].second != 0) doingSpeculative[w] = true
			}
		}
		log { "doing" + logDoing() }
		return false
	}

	val answer = List(n) { StringBuilder() }
	var stuckInARow = 0
	fun makeMovements(day: Int): Boolean {
		log { "$day" }
		checkTimeLimit()
		if (day == TO_DEBUG?.second) {
			log {}
		}
		if (day == answerLength) {
//			info { "\n$day infinite loop, doing ${logDoing()}".also { troubles.add(it) } }
			return true
		}

		if (assignForDay()) return true
		val workers = workers().toList()
		val plan = CharArray(n) { '.' }
		fun isForbiddenCorner(y: Int, x: Int): Boolean {
			if (x == n - 1 && (y == 0 || y == n - 1)) {
				val left = location(y, x - 1)
				if (occupiedByReserve[left] || left in location) return true
			}
			return false
		}
		fun matrix(): List<IntArray> {
			val matrix = List(workers.size) { IntArray(n * n) { infty } }
			val penaltyForbiddenCorner = n * n * 2000
			for (w in workers) {
				val job = doing[w]
				val v = location[w]
				if (job == -1) {
					for (u in possibleMoves(w)) {
						val x = locationX(u)
						val y = locationY(u)
						matrix[w][u] = x * 20 + abs(y - n / 2) * 4 + distUnloaded(v, u) +
								(if (isForbiddenCorner(y, x)) penaltyForbiddenCorner else 0)
					}
					continue
				}
				if (!holding[w]) {
					val vv = jobStartLocation(job)
					if (v == vv) {
						plan[w] = 'P'
						matrix[w][v] = 0
						continue
					}
					for (u in possibleMoves(w)) {
						val x = locationX(u)
						val y = locationY(u)
						val penaltyCornerSmall = if (locationX(vv) < n - 2 && x == n - 1) abs(y - n / 2) * 2 else 0
						val d = if (stuckInARow % (2 * n) < n) distUnloaded(u, vv) else distLoaded(u, vv)
						matrix[w][u] = (d + penaltyCornerSmall) * 100 +
								(if (isForbiddenCorner(y, x)) penaltyForbiddenCorner else 0)
					}
					continue
				}
				val vv = shouldBeFreeLocation[w]
				val isFinal = strategy[doing[w]] / code != 1
				val isSpeculative = doingSpeculative[w]
				require((isFinal && !isFirstInRow(doingWhat[w]) && !released[doingWhat[w] - 1]) == doingSpeculative[w])
				if (v == vv && !isSpeculative) {
					plan[w] = 'Q'
					matrix[w][v] = 0
					continue
				}
				val currentDist = distLoaded(v, vv)
				fun foregoerDistMax(): Int {
					var result = -1
					for (value in doingWhat[w] - 1 downTo 0) {
						val ww = workers.firstOrNull { doingWhat[it] == value } ?: break
						result = maxOf(result, distLoaded(location[ww], shouldBeFreeLocation[ww]))
						if (isFirstInRow(value)) break
					}
					return result
				}
				val foregoerDist = if (isSpeculative) foregoerDistMax() else -1
				for (u in possibleMoves(w)) {
					val newDist = distLoaded(u, vv)
					val bonusForRelease = if (isSpeculative) n * n else -n * n
					val bonusDist = newDist +
							(if (newDist > currentDist) 1 else if (newDist == 0) bonusForRelease else 0) +
							(if (newDist <= foregoerDist && u != v) n * n else 0)
					val bonusRoad = if (isTheRoad(u)) 0 else if (!isSpeculative) 1 else
						if (currentDist > foregoerDist) 100 else -100 * n * n
					val bonusSpeculativeOnReserve = if (isSpeculative && wantedReserve[u] && whereInReserve[doingWhat[w]] != u) 10 else 0
					val bonusForFinal = if (isFinal && !isSpeculative) 20 else 10
					val bonusWeird = (day + w) % n
					val value =	(((bonusDist + bonusSpeculativeOnReserve) * 100 + bonusRoad) /*- 50 / (newDist + 1)*/) *
							(bonusForFinal + bonusWeird)
					matrix[w][u] = value
				}
			}
			return matrix
		}
		val matrix = matrix()
		val h = hungarian(matrix)
		log { "h" + h.contentToString() }

		var todayStuckRight = false
		fun divert(stuck: List<Int>): Boolean {
			if (stuck.all { w -> holding[w] && strategy[doing[w]] / code == 1 }) {
				log { "can swap two going to reserve" }
				val (w, ww) = stuck
				val sw = shouldBeFreeLocation[w]
				whereInReserve[doingWhat[w]] = shouldBeFreeLocation[ww]
				shouldBeFreeLocation[w] = shouldBeFreeLocation[ww]
				whereInReserve[doingWhat[ww]] = sw
				shouldBeFreeLocation[ww] = sw
				h[w] = location[w] // can be improved
				h[ww] = location[ww]
				return true
			}
			val stuckRightHolding = stuck.all { locationX(location[it]) == n - 1 && holding[it] /*TODO*/ }
			val sorted = stuck.sortedBy { w ->
				val scoreImportance = if (holding[w] && !doingSpeculative[w]) {
					val scoreHoldingImportance = if (strategy[doing[w]] / code == 1) 3000 else {
						if ((shouldBeFreeLocation[w] == 4 || shouldBeFreeLocation[w] == 24) && stuck.all { locationY(location[it]) == 4 }) 2000 else 4000
					}
					scoreHoldingImportance - 10 * distUnloaded(location[w], shouldBeFreeLocation[w])
				} else {
					if (doing[w] == -1) 0 else 1000
				}
				val scoreLocation = -abs(locationY(location[w]) - 2)
				(scoreImportance + scoreLocation) * (if (stuckInARow % (2 * n) < n) 1 else -1)
			}
			for (w in sorted) {
				val ww = stuck[0] xor stuck[1] xor w
				val moves = possibleMoves(w).filter { where ->
					if (where in h) return@filter false
					if (isForbiddenCorner(locationY(where), locationX(where)) &&
						shouldBeFreeLocation[ww] == where &&
						where - 1 in location &&
						((where + locationFinish(n / 2)) / 2) in location) return@filter false
					if (stuckRightHolding) {
						return@filter false
//						if (locationX(where) == n - 1) return@filter false
//						if (doing[ww] >= 0 && strategy[doing[ww]] / code == 1 && (locationY(where) == 0 || locationY(where) == n - 1) /*&& occupiedByReserve[location(locationY(where), n - 2)]*/) return@filter false
//						if (doing[ww] >= 0 && strategy[doing[ww]] / code == 1) return@filter false
//						if (doing[ww] == -1) return@filter false
					}
					return@filter true
				}
				val newH = moves.minByOrNull { locationX(it) * 4 + abs(locationY(it) - n / 2) } ?: continue
				h[w] = newH
				return true
			}
			if (plan.contains('Q')) {
				for (w in stuck) h[w] = location[w]
				return true
			}
			val stuckRight = stuck.all { locationX(location[it]) == n - 1 }
			if (stuckRight || stuckRightHolding) {
				todayStuckRight = true
//				info { "$day stuckRight".also { troubles.add(it) } }
			}
			return false
		}
		var stuckToday = false
		var recountReserveDestinations = false
		val stuckPairs = buildList {
			for (w in workers) for (ww in workers) if (w < ww && h[w] == location[ww] && h[ww] == location[w]) {
				add(w to ww)
			}
		}
		for (stuckPair in stuckPairs) {
			val stuck = stuckPair.toList()
			stuckToday = true
			recountReserveDestinations = true
			if (!divert(stuck)) {
				for (w in stuck) h[w] = location[w]
				if (stuckInARow > 2 * n) {
					val isInRightColumn = stuck.all { w -> locationX(location[w]) == n - 1 }
					info { "$day error: stuck ${if (isInRightColumn) "" else "not right"} ${stuck.map { w -> doingWhat[w] }}, doing ${logDoing()}, wanting to ${shouldBeFreeLocation.contentToString()}".also { troubles.add(it) } }
					return true
				}
			}
		}
		fun workerAt(where: Int) = workers.firstOrNull { w -> location[w] == where }
		fun unstickRight() {
			val wImportant = mutableSetOf<Int>()
			val wCenter = workerAt(locationFinish(n / 2))
			var okForCenter = -1
			val doAfter: (() -> Unit)
			if (wCenter != null && stuckPairs.any { wCenter == it.first || wCenter == it.second }) {
				wImportant.add(wCenter)
				val pair = stuckPairs.first { wCenter == it.first || wCenter == it.second }
				val wOther = pair.first xor pair.second xor wCenter
				wImportant.add(wOther)
				h[wOther] = locationFinish(n / 2)
				val wThird = workerAt(location[wOther] * 2 - location[wCenter])
				if (wThird != null && distLoaded(location[wThird], shouldBeFreeLocation[wThird]) > distLoaded(location[wOther], shouldBeFreeLocation[wThird])) {
					wImportant.add(wThird)
					h[wThird] = location[wOther]
				}
				doAfter = {}
			} else {
				val w0 = workerAt(locationFinish(0))
				val w1 = workerAt(locationFinish(1))
				if (w0 != null && w1 != null) {
					wImportant.add(w0); wImportant.add(w1)
					doAfter = { h[w0] = locationFinish(1); h[w1] = locationFinish(2) }
					h[w0] = -1; h[w1] = -1
					okForCenter = locationFinish(3)
				} else {
					val w4 = workerAt(locationFinish(4))!!
					val w3 = workerAt(locationFinish(3))!!
					wImportant.add(w4); wImportant.add(w3)
					doAfter = { h[w4] = locationFinish(3); h[w3] = locationFinish(2) }
					h[w4] = -1; h[w3] = -1
					okForCenter = locationFinish(1)
				}
			}
			for (x in n - 1 downTo 1) {
				val w = workerAt(location(n / 2, x)) ?: break
				wImportant.add(w)
				if (h[w] == okForCenter && okForCenter !in location) break
				h[w] = location(n / 2, x - 1)
			}
			doAfter.invoke()
			if (h.toSet().size != h.size) {
				for (w in workers) {
					if (w in wImportant) continue
					h[w] = location[w]
				}
				require(h.toSet().size == h.size)
			}
		}
		if (todayStuckRight) {
			unstickRight()
		}
		if (h.contentEquals(location) && 'P' !in plan && 'Q' !in plan) {
			info { "$day stuck stale".also { if (troubles.none { t -> t.endsWith("stale") }) troubles.add(it) } }
			stuckInARow = maxOf(stuckInARow, n)
			stuckToday = true
		}
		if (stuckToday) stuckInARow++ else if (stuckInARow < n) stuckInARow = 0

		fun unstickCorner(y: Int) {
			if (todayStuckRight) return
			val c = locationFinish(y)
			val d = locationFinish((y + n / 2) / 2)
			val wd = workerAt(d) ?: return
			val wc = workerAt(c) ?: return
			if (shouldBeFreeLocation[wd] != c) return
			if (!holding[wd]) return
			if (h[wd] == c || h[wd] == d - 1 ) return
			if (h[wc] == c - 1) return
			if (doingWhat[wc] == doingWhat[wd] - 1) return
			if (isFirstInRow(doingWhat[wc])) return
			if (workers.any { location[it] == shouldBeFreeLocation[it] }) return
//			info { "\n$day unstickCorner".also { if (it !in troubles) troubles.add(it) } }
			val hwdOld = h[wd]; val hwcOld = h[wc]
			val doAfter = { h[wd] = c; h[wc] = c - 1 }
			h[wd] = -1; h[wc] = -1
			var canDo = true
			for (x in n - 2 downTo 1) {
				workerAt(location(y, x)) ?: break
				if (occupiedByReserve[location(y, x - 1)]) {
					canDo = false
					break
				}
			}
			if (!canDo) {
				h[wd] = hwdOld; h[wc] = hwcOld
//				info { "\n$day cannot unstickCorner".also { if (it !in troubles) troubles.add(it) } }
				unstickRight()
				return
			}
			val wImportant = mutableSetOf(wd, wc)
			for (x in n - 2 downTo 1) {
				val w = workerAt(location(y, x)) ?: break
				wImportant.add(w)
				h[w] = location[w] - 1
			}
			doAfter.invoke()
			if (h.toSet().size != h.size) {
				for (w in workers) {
					if (w in wImportant) continue
					h[w] = location[w]
				}
				require(h.toSet().size == h.size)
			}
		}
		unstickCorner(0)
		unstickCorner(n - 1)

		for (w in workers) {
			if (h[w] != location[w]) {
				plan[w] = DIR[dirTo(location[w], h[w])]
			}
		}
		if (done.all { it }) return true
		for (w in workers) {
			answer[w].append(plan[w])
			if (plan[w] == 'Q') {
				stuckInARow = 0
				done[doing[w]] = true
				doing[w] = -1
				val held = doingWhat[w]
				doingWhat[w] = -1
				shouldBeFreeTime[w] = -1
				shouldBeFreeLocation[w] = -1
				holding[w] = false
				if (locationX(h[w]) < n - 1) {
					occupiedByReserve[h[w]] = true
				} else {
					released[held] = true
					if (!isFirstInRow(held)) {
						if (!released[held - 1]) {
							info { "$day INVERSION".also { troubles.add(it) } }
						}
//						require(released[held - 1])
					}
					if (!isLastInRow(held)) {
						val ww = workers.firstOrNull { doingWhat[it] == held + 1 }
						if (ww != null) {
							doingSpeculative[ww] = false
						}
					}
				}
			} else if (plan[w] == 'P') {
				holding[w] = true
				beingHeld[doing[w]] = true
				shouldBeFreeTime[w] = distLoaded(h[w], shouldBeFreeLocation[w]) + 1
				occupiedByReserve[h[w]] = false
				wantedReserve[h[w]] = false
				if (doingWhat[w] == incoming[whichRow[doingWhat[w]]][0]) occupiedLeftPosition[whichRow[doingWhat[w]]] = false
			} else if (doing[w] == -1) {
				shouldBeFreeTime[w] = -1
			} else if (holding[w]) {
				shouldBeFreeTime[w] = distLoaded(h[w], shouldBeFreeLocation[w]) + 1
				if (strategy[doing[w]] / code == 1 && theReserve.contains(h[w]) && distLoaded(h[w], shouldBeFreeLocation[w]) >= distLoaded(location[w], shouldBeFreeLocation[w]) && shouldBeFreeLocation[w] != h[w]) {
					log { "$day onePlanChange" }
					val ww = workers.firstOrNull { ww -> shouldBeFreeLocation[ww] == h[w] } ?: -1
					require((ww == -1) == (!wantedReserve[h[w]]))
					val sw = shouldBeFreeLocation[w]
					if (ww == -1) {
						wantedReserve[sw] = false
						wantedReserve[h[w]] = true
					} else {
						shouldBeFreeLocation[ww] = sw
						whereInReserve[doingWhat[ww]] = sw
						shouldBeFreeTime[ww] = distLoaded(h[ww], sw) + 1
					}
					shouldBeFreeLocation[w] = h[w]
					whereInReserve[doingWhat[w]] = h[w]
					shouldBeFreeTime[w] = 1
				} else if (strategy[doing[w]] / code == 1 && distLoaded(h[w], shouldBeFreeLocation[w]) > distLoaded(location[w], shouldBeFreeLocation[w])) {
					log { "$day pushback" }
//					info { "$day pushback".also { troubles.add(it) } }
					recountReserveDestinations = true
				}

			} else {
				shouldBeFreeTime[w] = distUnloaded(location[w], jobStartLocation(doing[w])) + distLoaded(jobStartLocation(doing[w]), shouldBeFreeLocation[w]) + 2
			}
			location[w] = h[w]
		}

		for (w in workers) if (doing[w] != -1 && strategy[doing[w]] / code == 1 && plan[w] != 'Q') {
			val held = doingWhat[w]
			if (isFirstInRow(held)) continue
			val prev = held - 1
			if (released[prev]) {
//				info { "$day LOL1".also { troubles.add(it) } }
				log { "$day LOL1" }
				val locationReserve = shouldBeFreeLocation[w]
				val oldJob = doing[w]
				val newJob = strategy.indexOf((held + 2 * code).toShort())
				done[doing[w]] = true
				doing[w] = newJob
				beingDone[newJob] = true
				beingHeld[newJob] = beingHeld[oldJob]
				wantedReserve[locationReserve] = false
				shouldBeFreeLocation[w] = locationFinish(held / n)
				if (holding[w]) {
					shouldBeFreeTime[w] = distLoaded(location[w], shouldBeFreeLocation[w]) + 1
				} else {
					val jobStartLocation = jobStartLocation(oldJob)
					whereInReserve[held] = jobStartLocation
					shouldBeFreeTime[w] = distUnloaded(location[w], jobStartLocation) + distLoaded(jobStartLocation, shouldBeFreeLocation[w]) + 2
				}
				for (j in depends.indices) {
					for (i in depends[j].indices) {
						val job = depends[j][i] / 4
						if (job == oldJob) depends[j][i] = newJob * 4 + depends[j][i] % 4
					}
				}
			}
		}

		fun recountReserveDestinations() {
			val matrixReserve = List(workers.size) { IntArray(theReserve.size) { infty / n } }
			val recounted = BooleanArray(workers.size) { w -> doing[w] != -1 && strategy[doing[w]] / code == 1 && plan[w] != 'Q' }
			for (w in workers) if (recounted[w]) {
				require(wantedReserve[shouldBeFreeLocation[w]])
				wantedReserve[shouldBeFreeLocation[w]] = false
			}
			for (w in workers) if (recounted[w]) {
				for (rIndex in theReserve.indices) {
					val r = theReserve[rIndex]
					if (wantedReserve[r]) continue
					val speculativeWorkerThere = workerAt(r)
					val speculativeThere = (speculativeWorkerThere != null) && doingSpeculative[speculativeWorkerThere]
					matrixReserve[w][rIndex] = distLoaded(h[w], r) +
							(if (locationX(location[w]) >= n - 2 && locationX(r) < n - 2) n else 0) +
							(if (speculativeThere) n * n else 0)
				}
			}
			val hReserve = hungarian(matrixReserve)
			for (w in workers) if (recounted[w]) {
				val r = theReserve[hReserve[w]]
				shouldBeFreeLocation[w] = r
				whereInReserve[doingWhat[w]] = r
				shouldBeFreeTime[w] = distLoaded(h[w], r) + 1
				require(!wantedReserve[r])
				wantedReserve[r] = true
			}
		}
		if (recountReserveDestinations) {
			recountReserveDestinations()
		}

		log { "plan" + String(plan) }
		return false
	}

	for (day in 0..answerLength) {
		if (makeMovements(day)) break
	}
	return answer.takeIf { win }
}

@Suppress("BooleanLiteralArgument")
fun dependencies(incoming: List<IntArray>, strategy: ShortArray): List<MutableList<Int>> {
	val whichRow = IntArray(n * n)
	for (i in 0 until n) for (x in incoming[i]) whichRow[x] = i
	val firstJob = IntArray(n * n) { -1 }
	val secondJob = IntArray(n * n) { -1 }
	val lastJob = IntArray(n * n) { -1 }
	val byType = List(3) { mutableListOf<Int>() }
	for (i in strategy.indices) {
		val x = strategy[i] % code
		byType[strategy[i] / code].add(i)
		if (firstJob[x] == -1) {
			firstJob[x] = i
		} else {
			secondJob[x] = i
		}
		lastJob[x] = i
	}
	val depends = List(strategy.size) { mutableListOf<Int>() }
	fun depends(me: Int, they: Int, myFinish: Boolean, theirFinish: Boolean) {
		depends[me].add(they * 4 + (if (myFinish) 2 else 0) + (if (theirFinish) 1 else 0))
	}
	for (x in whichRow.indices) {
		if (secondJob[x] != -1) depends(secondJob[x], firstJob[x], false, true) // myStart > theirFinish
		if (!isFirstInRow(x)) depends(lastJob[x], lastJob[x - 1], true, true) // myFinish > theirFinish
		val row = whichRow[x]
		val index = incoming[row].indexOf(x)
		val y = incoming[row].getOrNull(index + 1) ?: continue
		depends(firstJob[x], firstJob[y], false, false) // myStart > theirStart
	}
	val (_, toReserve, fromReserve) = byType
	require(toReserve.size == fromReserve.size)
	for (i in maxReserveLimit until toReserve.size) {
		depends(toReserve[i], fromReserve[i - maxReserveLimit], true, false) // myFinish > theirStart
	}
	for (i in depends.indices) {
		val list = depends[i].toSet().sorted()
		depends[i].clear()
		depends[i].addAll(list)
	}
//	log { depends }
//	info { List(strategy.size) { i -> if (depends[i].isEmpty()) strategy[i].toString() else (depends[i].map { strategy[it / 4] }.toString().replace(" ", "") + strategy[i]) } }
	return depends
}

var maxReserve = 1
fun strategy(incoming: List<IntArray>): ShortArray {
	for (r in maxReserve..maxReserveLimit) {
		val s = strategy(incoming.map { it.toMutableList() }, mutableSetOf(), r)
		if (s == null) {
			maxReserve++
			continue
		}
		return s.reversed().toShortArray()
	}
	error("")
}

fun strategy(incoming: List<MutableList<Int>>, reserve: MutableSet<Int>, maxReserve: Int): MutableList<Short>? {
	if (reserve.isEmpty() && incoming.all { it.isEmpty() }) return mutableListOf()
	val where = IntArray(n * n) { -2 }
	for (i in 0 until n) for (x in incoming[i]) where[x] = i
	for (x in reserve) where[x] = -1
	val score = IntArray(n)
	for (rowOut in 0 until n) {
		val first = rowRange(rowOut).firstOrNull { where[it] != -2 } ?: continue
		val row = where[first]
		if (row == -1) {
			reserve.remove(first)
			val ans = strategy(incoming, reserve, maxReserve)
			ans?.add((first + 2 * code).toShort())
			reserve.add(first)
			return ans
		}
		if (incoming[row].last() == first) {
			incoming[row].removeLast()
			val ans = strategy(incoming, reserve, maxReserve)
			ans?.add(first.toShort())
			incoming[row].add(first)
			return ans
		}
		val howFar = (incoming[row].size - incoming[row].indexOf(first))
		score[row] += 1 shl (n - howFar)
	}
	for (row in 0 until n) if (score[row] != 0) score[row] += random.nextInt(1 shl n)
	if (reserve.size == maxReserve) return null
	for (row in (0 until n).sortedByDescending { score[it] }) {
		if (score[row] == 0) continue
		val x = incoming[row].removeLast()
		reserve.add(x)
		val ans = strategy(incoming, reserve, maxReserve)
		ans?.add((x + code).toShort())
		incoming[row].add(x)
		reserve.remove(x)
		if (ans != null) return ans
	}
	return null
}

fun isFirstInRow(x: Int) = x % n == 0
fun isLastInRow(x: Int) = x % n == n - 1
fun rowRange(row: Int) = (n * row) until (n * (row + 1))
fun location(y: Int, x: Int) = n * y + x
fun locationY(location: Int) = location / n
fun locationX(location: Int) = location % n
fun locationStart(y: Int) = location(y, 0)
fun locationFinish(y: Int) = location(y, n - 1)
fun dist(location1: Int, location2: Int) = abs(location1 / n - location2 / n) + abs(location1 % n - location2 % n)
fun nei(location: Int): List<Int> {
	val y = locationY(location); val x = locationX(location)
	return (0 until 4).mapNotNull { d ->
		val yy = y + DY[d]; val xx = x + DX[d]
		if (yy !in 0 until n || xx !in 0 until n) null else location(yy, xx)
	}
}
fun dirTo(location1: Int, location2: Int): Int {
	val dy = locationY(location2) - locationY(location1); val dx = locationX(location2) - locationX(location1)
	return (0 until 4).maxBy { DY[it] * dy + DX[it] * dx }
}
val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)
const val DIR = "RDLU"

private class TimeOutException : RuntimeException()

const val infty = Int.MAX_VALUE / 2
fun hungarian(matrix: List<IntArray>): IntArray {
	var e = matrix
	val ans = IntArray(e.size)
	Arrays.fill(ans, -1)
	if (e.isEmpty() || e[0].isEmpty()) return ans
	var swap = false
	if (e.size > e[0].size) {
		swap = true
		val f = List(e[0].size) { IntArray(e.size) }
		for (i in e.indices) for (j in e[0].indices) f[j][i] = e[i][j]
		e = f
	}
	val n1 = e.size
	val n2 = e[0].size
	val u = IntArray(n1 + 1)
	val v = IntArray(n2 + 1)
	val p = IntArray(n2 + 1)
	val way = IntArray(n2 + 1)
	for (i in 1..n1) {
		p[0] = i
		var j0 = 0
		val minV = IntArray(n2 + 1)
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
			if (swap) {
				ans[j - 1] = p[j] - 1
			} else {
				ans[p[j] - 1] = j - 1
			}
		}
	}
	return ans
}

fun Int.bit(index: Int) = ushr(index) and 1
fun Int.hasBit(index: Int) = bit(index) != 0

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	val n = `in`.readInt()
	val incoming = List(n) { `in`.readInts().reversed().toIntArray() }
	out.println(solve(incoming).joinToString("\n"))
	out.close()
	return null
}

private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
