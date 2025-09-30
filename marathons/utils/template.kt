package marathons.utils //TESTING

import java.io.*

private const val IS_INTERACTIVE = false
private val TO_EVAL = (0 until 10)
private val EVALUATOR: (() -> Unit)
		= { marathons.utils.evaluate(marathons.utils.atcoder.atcoderVisualizer(::solveIO, IS_INTERACTIVE), TO_EVAL) } //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
private val SUBMIT = EVALUATOR == null
private val VERBOSE = !SUBMIT
private var log: PrintWriter? = null
@Suppress("ComplexRedundantLet")
private val TIME_LIMIT = (2_000.also { TODO() } - 150)
	.let { it * marathons.utils.Evaluator.localTimeCoefficient((::solve).javaClass) } // TESTING
private var timeStart = 0L
private fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
private fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }
private class TimeOutException : RuntimeException()

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

private fun solveIO(`in`: BufferedReader, out: PrintWriter): List<Any>? {
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()
	val n = `in`.readInt()
	`in`.readInts()
	out.println(solve(n, toVisualize))
	out.close()
	return toVisualize
}

fun BufferedReader.readln() = readLine()!!
fun BufferedReader.readStrings() = readln().split(" ")
fun BufferedReader.readInt() = readln().toInt()
fun BufferedReader.readInts() = readStrings().map { it.toInt() }

private inline fun log(msg: () -> Any?) { log?.println(msg()) }
private inline fun info(msg: () -> Any?) { if (VERBOSE) msg().also { print("$it\t"); log { it } } }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.invoke()
		?: solveIO(System.`in`.bufferedReader(), PrintWriter(System.out, IS_INTERACTIVE))
}
