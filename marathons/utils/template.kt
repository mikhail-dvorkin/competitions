package marathons.utils //TESTING

import java.io.*
import java.util.concurrent.Callable

val EVALUATOR: Callable<Void?>
		= marathons.utils.Evaluator(marathons.utils.atcoder.Visualizer(::solve)) //TESTING
//		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
val VERBOSE = !SUBMIT
const val TIME_LIMIT = 10000 - 150
var timeStart = 0L
// TODO timeLimit *= Template._localTimeCoefficient
fun timePassed() = (System.currentTimeMillis() - timeStart) * 1.0 / TIME_LIMIT
fun checkTimeLimit(threshold: Double = 1.0) { if (timePassed() >= threshold) throw TimeOutException() }

fun solve(input: Int, toVisualize: MutableList<Any>?): Int {
	try {
		checkTimeLimit()
	} catch (_: TimeOutException) {
	}
	return input
}

class TimeOutException : RuntimeException()

fun solve(`in`: BufferedReader, out: Writer): List<Any>? {
	timeStart = System.currentTimeMillis()
	fun readLn() = `in`.readLine()!!
	fun readInt() = readLn().toInt()
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	val toVisualize = if (SUBMIT) null else mutableListOf<Any>()

	val n = readInt()
	readInts()
	out.write(solve(n, toVisualize))
	out.close()
	return toVisualize
}

private inline fun debug(msg: () -> Any) { if (VERBOSE) println(msg()) }

fun main() {
	@Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
	EVALUATOR?.apply { call() }
		?: solve(System.`in`.bufferedReader(), System.out.bufferedWriter() /* PrintWriter(System.out, true) */)
}
