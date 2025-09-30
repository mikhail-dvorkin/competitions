package marathons.atcoder.ahc26_stackOfBoxes //TESTING

import java.io.*
import kotlin.random.Random

private val TO_EVAL = (0 until 100)
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (2_000 - 120)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

private fun solve(input: List<List<Int>>): List<Pair<Int, Int>> {
	timeStart = System.currentTimeMillis()

	val random = Random(566)
	val m = input.size
	val n = input.sumOf { it.size }
	val a = List(m) { mutableListOf<Int>() }
	val where = IntArray(n)
	val whereIndex = IntArray(n)
	var score = 0
	val instructions = mutableListOf<Pair<Int, Int>>()
	var modeCode = ""
	var pCutBlock = true

	fun prepare(modeCodeParam: String) {
		modeCode = modeCodeParam
		for (i in a.indices) {
			a[i].clear()
			a[i].addAll(input[i])
			for (j in a[i].indices) {
				where[a[i][j]] = i
				whereIndex[a[i][j]] = j
			}
		}
		score = 0
		instructions.clear()
	}

	fun move(x: Int, index: Int) {
		val whereX = where[x]
		require(whereX != index)
		val aWhereX = a[whereX]
		val aIndex = a[index]
		val whereIndexX = whereIndex[x]
		score += aWhereX.size - whereIndexX + 1
		for (j in whereIndexX until aWhereX.size) {
			val y = aWhereX[j]
			where[y] = index
			whereIndex[y] = aIndex.size
			aIndex.add(y)
		}
		for (j in whereIndexX until aWhereX.size) {
			aWhereX.removeLast()
		}
		instructions.add(x to index)
	}

	val suffixMax = IntArray(n)
	val suffixMin = IntArray(n)
	fun moveClever(x: Int, index: Int) {
		val whereX = where[x]
		val whereIndexX = whereIndex[x]
		val aWhereX = a[whereX]
		var max = 0
		var min = n
		for (j in whereIndexX until aWhereX.size) {
			max = maxOf(max, aWhereX[j])
			min = minOf(min, aWhereX[j])
			suffixMax[j] = max
			suffixMin[j] = min
		}
		min = n
		for (j in aWhereX.size - 1 downTo whereIndexX + 1) {
			min = minOf(min, aWhereX[j])
			if (min > suffixMax[j - 1]
				|| pCutBlock && aWhereX[j - 1] == suffixMin[j - 1] && min > aWhereX[j - 1]
				) {
				move(aWhereX[j], index)
				min = n
			}
		}
		move(x, index)
	}

	fun moveAbove(x: Int, index: Int) {
		val whereX = where[x]
		require(whereX != index)
		val y = a[whereX].getOrNull(whereIndex[x] + 1) ?: return
		moveClever(y, index)
	}

	fun carryOut(x: Int) {
		val whereX = where[x]
		require(whereIndex[x] == a[whereX].size - 1)
		where[x] = -1
		a[whereX].removeLast()
		instructions.add(x to -1)
	}

	fun moveAboveAndCarryOut(x: Int, index: Int) {
		moveAbove(x, index)
		carryOut(x)
	}

	var bestScore = Int.MAX_VALUE
	var answer = listOf<Pair<Int, Int>>()
	var bestModeCode = ""
	fun solved() {
		if (score < bestScore) {
			bestScore = score
			answer = instructions.toList()
			bestModeCode = modeCode
			info { "$modeCode Score:=$score" }
		} else {
//			info { "Score?=$score" }
		}
	}

	var furthestReason = 0
	fun furthest(searchStart: Int, toRemove: List<Int>): Int {
		val toCandidates = (0 until m).toMutableSet()
		toCandidates.removeAll(toRemove.toSet())
		furthestReason = n + 1
		for (y in searchStart until n) {
			if (toCandidates.size == 1) {
				furthestReason = y
				break
			}
			toCandidates.remove(where[y])
		}
		return toCandidates.first()
	}

	fun carryOutGreedy(x: Int) {
		moveAboveAndCarryOut(x, furthest(x + 1, listOf(where[x])))
	}

	fun solveGreedy() {
		prepare("greedy")
		for (x in 0 until n) {
			carryOutGreedy(x)
		}
		solved()
	}

	fun solveWithSavingForLater() {
		pCutBlock = random.nextBoolean()
		val pGarbageCount = 1 + random.nextInt(2)
		val pLow = n / 3 + random.nextInt(n / 4)
//		val pMiddleShuffle = n / 40
		val garbageAtLeast = if (pGarbageCount == 1) {
			listOf(pLow)
		} else {
			listOf((pLow + n) / 2 /*+ pMiddleShuffle - random.nextInt(2 * pMiddleShuffle + 1)*/, pLow)
		}
		val p1 = (n / 4 + random.nextInt(n / 4)).let {
			if (garbageAtLeast.isEmpty()) it else minOf(it, garbageAtLeast.last() - 1)
		}
		val p2 = n / 4 + random.nextInt(n / 4)
		prepare("${garbageAtLeast.lastOrNull() ?: 0},$pGarbageCount,$p1,$p2,$pCutBlock")

		val garbageIndex = (0 until m).shuffled(random).take(garbageAtLeast.size)
		for (i in garbageIndex) {
			moveClever(a[i][0], furthest(0, garbageIndex))
		}
		for (x in 0 until n) {
			val whereX = where[x]
			val whereIndexX = whereIndex[x]

			var groupBottom = -1
			var groupTo = -1
			val furthestMarker = -2
			tailrec fun send() {
				if (groupBottom == -1) return
				if (groupTo == furthestMarker) {
					val actualGarbage = garbageAtLeast.count { it >= x }
					val toRemove = garbageIndex.take(actualGarbage) + whereX
					groupTo = furthest(x + 1, toRemove)
					furthest(x + 1, toRemove + groupTo)
					var smallest = groupBottom
					for (i in a[whereX].indices.reversed()) {
						val y = a[whereX][i]
						if (y == groupBottom) break
						if (y < furthestReason && i != a[whereX].lastIndex) {
							smallest = y
							break
						}
					}
					if (smallest != groupBottom) {
						moveAbove(smallest, groupTo)
						groupTo = furthestMarker
						return send()
					}
				}
				moveClever(groupBottom, groupTo)
			}
			fun to(y: Int): Int {
				if (x >= p1) return furthestMarker
				if (y <= x + p2) return furthestMarker
				val i = garbageAtLeast.indices.firstOrNull { y >= garbageAtLeast[it] }
				if (i != null) {
					if (garbageIndex[i] == whereX) return furthestMarker
					return garbageIndex[i]
				}
				return furthestMarker
			}
			for (i in a[whereX].size - 1 downTo whereIndexX + 1) {
				val y = a[whereX][i]
				val to = to(y)
				if (to != groupTo) {
					send()
					groupTo = to
				}
				groupBottom = y
			}
			send()
			carryOut(x)
		}
		solved()
	}

	solveGreedy()
	try {
		while (true) {
			solveWithSavingForLater()
			checkTimeLimit()
		}
	} catch (_: TimeOutException) {
	}
	info { "$bestModeCode Score=$bestScore" }
	return answer
}

private class TimeOutException : RuntimeException()

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	val (_, m) = `in`.readInts()
	val b = List(m) { `in`.readInts().map { it - 1 } }
	val instructions = solve(b)
	out.println(instructions.joinToString("\n") { "${it.first + 1} ${it.second + 1}" })
	out.close()
	return null
}

inline fun log(msg: () -> Any) { if (VERBOSE) System.err.println(msg()) }
inline fun info(length: Int, msg: () -> Any) = info { msg().toString().padEnd(length) }
inline fun info(msg: () -> Any) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
