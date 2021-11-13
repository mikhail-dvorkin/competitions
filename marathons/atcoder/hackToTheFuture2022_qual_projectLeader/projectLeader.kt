package marathons.atcoder.hackToTheFuture2022_qual_projectLeader

import java.io.*
import java.util.concurrent.Callable

val EVALUATOR: Callable<Void?>
//		= marathons.utils.Evaluator(marathons.utils.atcoder.Visualizer(::solve)) //TESTING
		?= null //SUBMISSION
@Suppress("SENSELESS_COMPARISON")
val SUBMIT = EVALUATOR == null
const val TIME_LIMIT = 3000 - 150
const val MAX_DAYS = 2000

fun solve(`in`: BufferedReader, out: BufferedWriter) {
	fun readLn() = `in`.readLine()!!
	fun readStrings() = readLn().split(" ")
	fun readInts() = readStrings().map { it.toInt() }
	fun debug(s: String) = if (!SUBMIT) println(s) else Unit

	val (tasksAmount, workers, skills, edgesAmount) = readInts()
	val tasks = List(tasksAmount) { readInts() }
	val edges = List(edgesAmount) { readInts().map { it - 1 } }

	val (neiBackward, neiForward) = List(2) { List(tasksAmount) { mutableListOf<Int>() } }
	val depending = IntArray(tasksAmount)
	for ((v, u) in edges) {
		neiForward[v].add(u)
		neiBackward[u].add(v)
		depending[u]++
	}

	val taskComplexity = tasks.map {
		it.maxOrNull()!!
	}
	val importance = IntArray(tasksAmount)
	for (v in tasks.indices.reversed()) {
		for (u in neiForward[v]) {
			importance[v] = maxOf(importance[v], importance[u])
		}
		importance[v] += taskComplexity[v]
	}
	val readyToStart = tasks.indices.filter { depending[it] == 0 }.toMutableSet()
	val doing = IntArray(workers) { -1 }
	val started = IntArray(workers) { -1 }
	val free = doing.indices.toMutableSet()
	val workerDid = IntArray(workers)
	val workerSum = IntArray(workers)
	fun workerQuality(worker: Int): Double {
		return workerSum[worker] / (workerDid[worker] + 0.125)
	}

	for (day in 0..MAX_DAYS) {
		val tasksNow = readyToStart.sortedByDescending { importance[it] }
		val workersNow = free.sortedByDescending { workerQuality(it) }
		val pairs = workersNow.zip(tasksNow)
		out.write("${pairs.size} ${pairs.joinToString(" ") { "${it.first + 1} ${it.second + 1}" }}")
		out.newLine()
		out.flush()
		pairs.forEach { (worker, task) ->
			free.remove(worker)
			doing[worker] = task
			started[worker] = day
			readyToStart.remove(task)
		}

		val input = readInts()
		if (input[0] == -1) break
		val workersFinished = input.drop(1).map { it - 1 }
		for (worker in workersFinished) {
			val v = doing[worker]
			free.add(worker)
			for (u in neiForward[v]) if (--depending[u] == 0) readyToStart.add(u)
			workerDid[worker]++
			workerSum[worker] += day - started[worker] + 1
			doing[worker] = -1
			started[worker] = -1
		}
	}
}

fun main() {
	@Suppress("UNNECESSARY_SAFE_CALL")
	EVALUATOR?.call() ?: solve(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
