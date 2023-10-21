package marathons.utils //TESTING

import java.io.*
import java.util.concurrent.Callable

private const val IS_INTERACTIVE = false
private val TO_EVAL = (0 until 10)
private val EVALUATOR: () -> Unit
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO, IS_INTERACTIVE), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
private val TIME_LIMIT = 10000.also { TODO() } - 150
private var timeStart = 0L
// TODO timeLimit *= Template._localTimeCoefficient
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

private fun solve(input: Int, toVisualize: MutableList<Any>?): Int {
	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
	timeStart = System.currentTimeMillis()
	try {
		checkTimeLimit()
	} catch (_: TimeOutException) {
	}
	log?.close()
	return input
}

private class TimeOutException : RuntimeException()

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	fun readLn() = `in`.readLine()!!
	fun readInt() = readLn().toInt()
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()

	val n = readInt()
	readInts()
	out.println(solve(n, toVisualize))
	out.close()
	return toVisualize
}

private inline fun log(msg: () -> Any) { log?.println(msg()) }
private inline fun info(msg: () -> Any) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out, IS_INTERACTIVE))
}
