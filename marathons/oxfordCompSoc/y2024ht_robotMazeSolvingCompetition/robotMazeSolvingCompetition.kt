package marathons.oxfordCompSoc.y2024ht_robotMazeSolvingCompetition //TESTING

import java.io.*

private val TO_EVAL = -3 until 100
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(::robotMazeSolvingCompetitionEval, TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
private val TIME_LIMIT = 5_000 - 150
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

private fun solve(maze: List<String>, maskString: String): String {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()

	val squareSize = 7
	info { maskString.take(30) }
	val mask = BooleanArray(maskString.length) { maskString[it] == '?' }
	val n = maze.size

	fun encode(y: Int, x: Int) = y * n + x
	fun decode(cell: Int) = cell / n to cell % n
	val isWall = BooleanArray(n * n) { cell ->
		val (yCell, xCell) = decode(cell)
		maze[yCell][xCell] == '#'
	}
	val nei = List(n * n) { cell ->
		val (yCell, xCell) = decode(cell)
		if (isWall[cell]) return@List intArrayOf()
		IntArray(4) { d ->
			val y = yCell + DY[d]
			val x = xCell + DX[d]
			encode(y, x).let { if (isWall[it]) -1 else it }
		}
	}
	fun afterMove(cell: Int, d: Int) = nei[cell][d].let { if (it == -1) cell else it }

	fun encodePair(cell1: Int, cell2: Int): Int = if (cell1 <= cell2) cell1 * n * n + cell2 else encodePair(cell2, cell1)
	fun decodePair(code: Int) = code / (n * n) to code % (n * n)
	val joiningMove = IntArray(n * n * n * n)
	fun joiningBfs(): IntArray {
		fun pairMoved(cell1: Int, cell2: Int, d: Int): Int {
			val cell3 = afterMove(cell1, d)
			val cell4 = afterMove(cell2, d)
			val pairTo = encodePair(cell3, cell4)
			return pairTo
		}
		fun moveTowards(pairFrom: Int, pairTo: Int): Int {
			val (cell1, cell2) = decodePair(pairFrom)
			return (0 until 4).first { d -> pairMoved(cell1, cell2, d) == pairTo }
		}
		val neiBack = List(n * n * n * n) { mutableListOf<Int>() }
		for (cell1 in 0 until n * n) {
			if (isWall[cell1]) continue
			for (cell2 in 0 until cell1) {
				if (isWall[cell2]) continue
				val pairFrom = encodePair(cell1, cell2)
				for (d in 0 until 4) {
					val pairTo = pairMoved(cell1, cell2, d)
					neiBack[pairTo].add(pairFrom)
				}
			}
		}
		val dist = IntArray(n * n * n * n) { -1 }
		val queue = IntArray(n * n * n * n)
		var low = 0; var high = 0
		for (cell in 0 until n * n) {
			if (isWall[cell]) continue
			val init = encodePair(cell, cell)
			dist[init] = 0
			queue[high++] = init
		}
		while (low < high) {
			val pair1 = queue[low++]
			for (pair2 in neiBack[pair1]) {
				if (dist[pair2] != -1) continue
				dist[pair2] = dist[pair1] + 1
				queue[high++] = pair2
				joiningMove[pair2] = moveTowards(pair2, pair1)
			}
		}
		return dist
	}
	val joiningDist = joiningBfs()
	fun joinablePair(cells: List<Int>): Int {
		var joinablePair = -1
		var joinablePairScore = Int.MAX_VALUE
		for (i in cells.indices) for (j in 0 until i) {
			val pair = encodePair(cells[i], cells[j])
			val score = joiningDist[pair]
			require(score != -1)
			if (score < joinablePairScore) {
				joinablePair = pair
				joinablePairScore = score
			}
		}
		return joinablePair
	}

	val dirToFinish = IntArray(n * n)
	fun bfs(init: Int): Pair<IntArray, IntArray> {
		if (isWall[init]) return intArrayOf() to intArrayOf()
		val dist = IntArray(n * n) { -1 }
		val queue = IntArray(n * n)
		dist[init] = 0
		queue[0] = init
		var low = 0; var high = 1
		while (low < high) {
			val cell = queue[low++]
			for (d in 0 until 4) {
				val cell2 = nei[cell][d]
				if (cell2 == -1 || dist[cell2] != -1) continue
				dist[cell2] = dist[cell] + 1
				queue[high++] = cell2
				dirToFinish[cell2] = d xor 2
			}
		}
		return dist to queue
	}
	val bfsResults = List(n * n) { bfs(it) }
	val dist = bfsResults.map { it.first }
//	val bfsQueues = bfsResults.map { it.second }
	val start = encode(1, 1)
	val finish = encode(n - 2, n - 2)
	val distFinish = dist[finish]

	var listTooLarge: List<Int>? = null
	fun Cells(list: List<Int>): Cells {
		listTooLarge = list
		if (list.isEmpty()) return -2
		val yMin = list.minOf { decode(it).first }
		val xMin = list.minOf { decode(it).second }
		var result = (yMin.toLong() shl 59) or (xMin.toLong() shl 54)
		for (cell in list) {
			val (yCell, xCell) = decode(cell)
			val y = yCell - yMin
			if (y !in 0 until squareSize) return -1
			val x = xCell - xMin
			if (x !in 0 until squareSize) return -1
			result = result.setBit(y * squareSize + x)
		}
		listTooLarge = null
		return result
	}
	fun Cells.size() = this.shl(10).countOneBits()
	fun Cells.yxMin(): Pair<Int, Int> {
		val yMin = (this ushr 59).toInt()
		val xMin = ((this shl 5) ushr 59).toInt()
		return yMin to xMin
	}
	fun Cells.cellMin(): Int {
		val (yMin, xMin) = yxMin()
		return encode(yMin, xMin)
	}
	fun Cells.forEach(action: (Int, Int) -> Unit) {
		val (yMin, xMin) = yxMin()
		val bits = ((this shl 10) ushr 10).countSignificantBits()
		for (index in 0 until bits) {
			if (!this.hasBit(index)) continue
			action(yMin + index / squareSize, xMin + index % squareSize)
		}
	}
	fun Cells.toList(): List<Int> {
		if (this == -2L) return listOf()
		val cellsList = mutableListOf<Int>()
		forEach { y, x -> cellsList.add(encode(y, x)) }
		return cellsList
	}

	val memoMoved = mutableMapOf<Cells, Cells>()
	fun Cells.moved(d: Int): Cells {
		val code = this or (d.toLong() shl 52)
		val remembered = memoMoved[code]
		if (remembered != null) return remembered
		// TODO rewrite without lists
		val new = mutableListOf<Int>()
		this.forEach { y, x ->
			val cell = encode(y, x)
			val newCell = afterMove(cell, d)
			if (newCell != finish) new.add(newCell)
		}
		val result = Cells(new)
		if (result != -1L) memoMoved[code] = result
		return result
	}
	fun List<Int>.moved(d: Int): Cells {
		val new = mutableListOf<Int>()
		for (cell in this) {
			val newCell = afterMove(cell, d)
			if (newCell != finish) new.add(newCell)
		}
		return Cells(new)
	}
	val memoAfterQuestionMark = mutableMapOf<Cells, Cells>()
	fun Cells.afterQuestionMark(): Cells {
		val remembered = memoAfterQuestionMark[this]
		if (remembered != null) return remembered
		// TODO rewrite without lists
		val new = mutableListOf<Int>()
		this.forEach { y, x ->
			val cell = encode(y, x)
			for (d in 0 until 4) {
				val newCell = afterMove(cell, d)
				if (newCell != finish) new.add(newCell)
			}
		}
		val result = Cells(new)
		if (result != -1L) memoAfterQuestionMark[this] = result
		return result
	}
	fun List<Int>.afterQuestionMark(): Cells {
		val new = mutableListOf<Int>()
		for (cell in this) {
			for (d in 0 until 4) {
				val newCell = afterMove(cell, d)
				if (newCell != finish) new.add(newCell)
			}
		}
		return Cells(new)
	}

	val init = Cells(listOf(start))

	val answer = StringBuilder()
	fun answerChar(c: Char) {
		answer.append(c)
		log { "MOVE: $c" }
	}
	fun answer(d: Int) = answerChar(DIR[d])
	fun answerQuestion() = answerChar('?')

	fun magicScore(cellsNew: List<Int>, cellsOldSize: Int): Double {
		if (cellsNew.isEmpty()) return -1e100
		var count = 0
		var sum = 0
		var max = 0
		var maxDistToFinish = 0
		for (i in cellsNew.indices) {
			maxDistToFinish = maxOf(maxDistToFinish, distFinish[cellsNew[i]])
			for (j in 0 until i) {
				val x = dist[cellsNew[i]][cellsNew[j]]
				count++
				sum += x
				max = maxOf(max, x)
			}
		}
		val magicScore = max * 1e6 + cellsNew.size * 1e4 + maxDistToFinish * 1e2 + sum / (count + 0.1)
		return magicScore
	}
	fun searchOneMoveForGroup(cells: List<Int>, depth: Int) = (0 until (1 shl (2 * depth))).minBy { path ->
		var p = path
		var c = cells
		var improvementScore = 0
		repeat(depth) {
			val cellsNewOptimistic = c.moved(p % 4)
			p /= 4
			c = if (cellsNewOptimistic != -1L) cellsNewOptimistic.toList() else listTooLarge!!.toSet().toList()
			if (it == 0) improvementScore = c.size.compareTo(cells.size) + 1
		}
		magicScore(c, cells.size) + improvementScore * 1e9
	}.let { it % 4 }
	fun bestMoveForGroup(cellsIn: List<Int>): Int {
		val cells = cellsIn.toSet().sorted()
		log { "det cells $cells" }
		val jPair = joinablePair(cells)
		val jDist = joiningDist[jPair]
		val jMove = joiningMove[jPair]
		val fClosest = cells.minBy { distFinish[it] }
		val fDist = distFinish[fClosest]
		val fMove = dirToFinish[fClosest]
		log { "need j$jDist / f$fDist" }
		if (jDist > 1) return if (jDist < fDist) jMove else fMove
		val magicDepth = if (cells.size > squareSize * squareSize) 1 else 2
		return searchOneMoveForGroup(cells, magicDepth)
	}

	val memoBestMove = mutableMapOf<Cells, Int>()
	tailrec fun solve(cells: Cells, zStart: Int) {
		val zNextQuestion = (zStart until mask.size).firstOrNull { mask[it] } ?: mask.size
		val withoutQuestions = zNextQuestion - zStart
		require(withoutQuestions >= 0)
		log { "=== z=$zStart === , have ${withoutQuestions}" }
		log { "$cells (size ${cells.size()})" }
		log { listTooLarge }
		if (cells == -2L) return
		if (cells == -1L) {
			val cellsList = listTooLarge!!.toSet().toList()
			if (withoutQuestions == 0) {
				answerQuestion()
				return solve(cellsList.afterQuestionMark(), zStart + 1)
			}
			val move = bestMoveForGroup(cellsList)
			answer(move)
			return solve(cellsList.moved(move), zStart + 1)
		}
		val cellsSize = cells.size()
		if (withoutQuestions == 0) {
			answerQuestion()
			return solve(cells.afterQuestionMark(), zStart + 1)
		}
		if (cellsSize == 1) {
			val cell = cells.cellMin()
			if (withoutQuestions >= distFinish[cell]) {
				val move = dirToFinish[cell]
				answer(move)
				return solve(cells.moved(move), zStart + 1)
			}
			val move = dirToFinish[cell]
			answer(move)
			return solve(cells.moved(move), zStart + 1)
		} else {
			val move = memoBestMove.getOrPut(cells) { bestMoveForGroup(cells.toList()) }
			answer(move)
			return solve(cells.moved(move), zStart + 1)
		}
	}
	solve(init, 0)

	try {
		checkTimeLimit()
	} catch (_: TimeOutException) {
	}
	log?.close()
	return answer.toString()
}

typealias Cells = Long

private fun Long.bit(index: Int) = ushr(index).toInt() and 1
private fun Long.hasBit(index: Int) = bit(index) != 0
private fun Long.setBit(index: Int) = or(1L shl index)
private fun Long.countSignificantBits() = Long.SIZE_BITS - java.lang.Long.numberOfLeadingZeros(this)

val DY = intArrayOf(0, 1, 0, -1)
val DX = intArrayOf(1, 0, -1, 0)
const val DIR = "RDLU"

private class TimeOutException : RuntimeException()

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()

fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any> {
//	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()
	val n = `in`.readInt()
	val maze = List(n) { `in`.readLine() }
	`in`.readInt()
	val mask = `in`.readln()
	val answer = solve(maze, mask)
	out.println(answer)
	out.close()
	return listOf(answer)
}

private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out))
}
