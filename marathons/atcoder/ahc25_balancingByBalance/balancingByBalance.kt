package marathons.atcoder.ahc25_balancingByBalance //TESTING

import java.io.*
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

private val TO_EVAL = 0 until 10
const val USE_MY_EVAL = true
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO, true), TO_EVAL, processor) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT
private val artifacts: MutableList<Any>? = if (VERBOSE) mutableListOf() else null
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (1_000 - 150)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

typealias Group = List<Int>

fun solve(judge: Judge): List<Any>? {
	artifacts?.clear()
	timeStart = System.currentTimeMillis()
	val random = Random(566)
	val (n, parts, questions) = judge.getParameters()

	val pWantedGroup = 0.8 * sqrt(n.toDouble()).roundToInt()//.coerceAtLeast(parts)
	val pGreedyUsage = 0.4

	info { "n=$n" }
	info(4) { "p=$parts" }
	info { "q=$questions" }
	info(8) { "q/n=${(questions * 10.0 / n).roundToInt() / 10.0}" }

	var questionsLeft = questions
	val memo = mutableMapOf<Long, Int>()
	val countChapters = mutableMapOf<String, Int>()
	fun ask(left: Group, right: Group, chapter: String): Int {
		val hashLeft = left.sorted().hashCode()
		val hashRight = right.sorted().hashCode()
		if (hashLeft == hashRight) return 0
		val (hash, coef) = if (hashLeft < hashRight) {
			(hashLeft with hashRight) to 1
		} else {
			(hashRight with hashLeft) to -1
		}
		val fromMemo = memo[hash]
		if (fromMemo != null) return fromMemo * coef

		val result = if (questionsLeft == 0) {
			info { "EXHAUSTED" }
			if (random.nextBoolean()) -1 else 1
		} else {
			questionsLeft--
			countChapters[chapter] = countChapters.getOrDefault(chapter, 0) + 1
			judge.compare(left.toIntArray(), right.toIntArray())
				.takeIf { it != 0 } ?: left.min().compareTo(right.min())
		}
		memo[hash] = result * coef
		return result
	}
	fun ask(left: Int, right: Int, chapter: String) = ask(listOf(left), listOf(right), chapter)
	fun askQsort(left: Int, right: Int) = ask(left, right, "qsort")

	fun split(group: List<Int>): List<MutableList<Int>> {
		val pivotAmount = if (group.size >= 64) 5 else if (group.size >= 16) 3 else 1
		val pivots = mutableSetOf<Int>()
		while (pivots.size < pivotAmount) pivots.add(group[random.nextInt(group.size)])
		val pivotsSorted = pivots.sortedWith(::askQsort)
		val pivot = pivotsSorted[pivotAmount / 2]
		val result = List(3) { mutableListOf<Int>() }
		for (x in group) {
			result[askQsort(x, pivot) + 1].add(x)
		}
		return result.filter { it.isNotEmpty() }
	}

	val increasing = mutableListOf(List(n) { it })
	fun splitAt(index: Int) {
		require(increasing[index].size > 1)
		val new = split(increasing[index])
		increasing.removeAt(index)
		increasing.addAll(index, if (new.size > 1) new else new[0].map { mutableListOf(it) })
	}

	while (true) {
		var ofSizeOne = 0
		var j = increasing.lastIndex
		while (j >= 0 && increasing[j].size == 1) {
			j--
			ofSizeOne++
		}
		if (ofSizeOne >= parts) break
		splitAt(j)
	}

	val leaveForGreedy = ((n - parts) * parts * pGreedyUsage).roundToInt()
	fun shouldSplitAt(index: Int) = questionsLeft > leaveForGreedy + increasing[index].size
	tailrec fun splitMore() {
		for (i in increasing.indices) {
			if (increasing[i].size <= pWantedGroup) continue
			if (shouldSplitAt(i)) { splitAt(i); return splitMore() }
		}
		val i = increasing.indexOfFirst { it.size != 1 }
			.takeIf { it != -1 } ?: increasing.size
		if (i < parts && shouldSplitAt(i)) { splitAt(i); return splitMore() }
	}
	splitMore()
	val weightLevel = IntArray(n)
	for (i in increasing.indices) for (x in increasing[i]) weightLevel[x] = i
	info { increasing.map { it.size } }

	val greedy = increasing.takeLast(parts).map { it.toMutableList() }.toMutableList()
	fun siftUp(index: Int, chapter: String, rightBorder: Int) {
		val i = (index..rightBorder).binarySearch { i ->
			ask(greedy[index], greedy[i], chapter) < 0
		} - 1
		val temp = greedy[index]
		for (j in index until i) greedy[j] = greedy[j + 1]
		greedy[i] = temp
	}
	fun siftDown(index: Int, chapter: String, leftBorder: Int) {
		val i = (leftBorder..index).binarySearch { i ->
			ask(greedy[index], greedy[i], chapter) < 0
		}
		val temp = greedy[index]
		for (j in index downTo i + 1) greedy[j] = greedy[j - 1]
		greedy[i] = temp
	}
	for (group in increasing.dropLast(parts).reversed()) {
		for (x in group) {
			greedy[0].add(x)
			if (questionsLeft == 0) {
				val temp = greedy[0]
				for (i in 0..parts - 2) greedy[i] = greedy[i + 1]
				greedy[parts - 1] = temp
				continue
			}
			siftUp(0, "greedy", parts)
		}
	}
//	fun sorted(list: List<Group>): List<Group> {
//		return list.sortedWith(::ask)
//	}
//	val all = List(n) { listOf(it) as Group }
//	val sorted = sorted(all)
	info { questionsLeft }

	var improvedB = false
	tailrec fun improveAB(small: Int, big: Int) {
		val listY = greedy[big]
			.filter { if (improvedB) weightLevel[it] <= 2 else true }
			.sortedBy { weightLevel[it] }
		for (y in listY) {
			if (questionsLeft == 0) return
			checkTimeLimit()
			if (greedy[big].size == 1) break
			if (ask(greedy[small] + y, greedy[big] - y, "improveA") >= 0) continue
			greedy[small].add(y)
			greedy[big].remove(y)
			info { "iA${weightLevel[y]}//$questionsLeft" }
			siftDown(big, "improveA", small)
			siftUp(small, "improveA", big + 1)
			return improveAB(small, big)
		}
		val listYX = greedy[big].cartesianProduct(greedy[small])
			.filter { (weightLevel[it.first] - weightLevel[it.second]) in 1..2 }
			.sortedBy { weightLevel[it.first] - weightLevel[it.second] }
		for ((y, x) in listYX) {
			if (questionsLeft == 0) return
			checkTimeLimit()
			if (ask(greedy[small] + y - x, greedy[big] - y + x, "improveB") >= 0) continue
			greedy[small].add(y)
			greedy[small].remove(x)
			greedy[big].add(x)
			greedy[big].remove(y)
			improvedB = true
			info { "iB${weightLevel[y] - weightLevel[x]}" }
			siftDown(big, "improveB", small)
			siftUp(small, "improveB", big + 1)
			return improveAB(small, big)
		}
		if (small == 0 && big == 1) return
		if (small == 0) return improveAB(parts - big, parts - 1)
		return improveAB(small - 1, big - 1)
	}
	try {
		improveAB(0, parts - 1)
	} catch (_: Exception) {
	}

	while (questionsLeft > 0) {
		val shuffled = List(n) { it }.shuffled()
		ask(shuffled.take(n / 2), shuffled.drop(n / 2), "trash")
	}
	info { countChapters }
//	stat("greedyUsage", countChapters["greedy"]!! * 1.0 / (n - parts) / parts)
	val partId = IntArray(n)
//	for (p in 1 until parts) partId[increasing[increasing.size - p][0]] = p
	for (p in 0 until parts) for (x in greedy[p]) partId[x] = p
	judge.putAnswer(partId)
	//stat("time", System.currentTimeMillis() - timeStart)
	return artifacts
}

private fun solveIO(`in`: BufferedReader, out: PrintWriter) = solve(JudgeIO(`in`, out))

interface Judge {
	fun canRevealSecrets() = false
	fun getParameters(): List<Int>
	fun compare(left: IntArray, right: IntArray): Int
	fun putAnswer(partId: IntArray)
}

private class JudgeIO(val `in`: BufferedReader, val out: PrintWriter) : Judge {
	fun readln() = `in`.readLine()!!
	fun readStrings() = readln().split(" ")
	fun readInts() = readStrings().map { it.toInt() }

	override fun getParameters(): List<Int> = readInts()

	override fun compare(left: IntArray, right: IntArray): Int {
		val list = listOf(left.size, right.size) + left.toList() + right.toList()
		out.println(list.joinToString(" "))
		return "<=>".indexOf(readln()) - 1
	}

	override fun putAnswer(partId: IntArray) = out.println(partId.joinToString(" "))
}

private class TimeOutException : RuntimeException()

inline fun log(msg: () -> Any) { if (VERBOSE) System.err.println(msg()) }
inline fun info(length: Int, msg: () -> Any) = info { msg().toString().padEnd(length) }
inline fun info(msg: () -> Any) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }
private fun stat(key: String, value: Number) { if (VERBOSE) { artifacts?.add(key to value.toDouble()); info { "$key=$value" } } }

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private fun <T, R> Iterable<T>.cartesianProduct(other: Iterable<R>) = flatMap { x -> other.map { y -> x to y } }
private infix fun Int.with(that: Int) = (toLong() shl 32) xor that.toLong()

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out, true))
}
