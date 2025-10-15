package facebook.y2025.practice

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private data class InputA(val a: List<Int>, val b: List<Int>)

private fun solve(input: InputA): String {
	val (a, b) = input
	val where = mutableMapOf<Int, Int>()
	for (i in a.indices) where[a[i]] = i
	val ans = mutableListOf<Pair<Int, Int>>()
	a.withIndex().sortedBy { it.value }.forEach { entry ->
		val i = entry.index
		if (b[i] < a[i]) return "-1"
		if (b[i] == a[i]) return@forEach
		val needWhere = where[b[i]] ?: return "-1"
		ans.add(i to needWhere)
	}
	return "${ans.size}\n${ans.joinToString("\n") { "${it.first + 1} ${it.second + 1}" }}"
}

private fun read(): InputA {
	readInt()
	val a = readInts()
	val b = readInts()
	return InputA(a, b)
}

fun main() {
	val nThreads = 4
	val formatOut = "Case #%2\$d: %1\$s"
	val inputFileName = "input.txt"
	val outputFileName = "output.txt"

	System.setIn(File(inputFileName).inputStream())
	val out = PrintWriter(outputFileName)
	val tests = readInt()
	val callables = List(tests) { t ->
		val input = read()
		Callable {
			val answer = solve(input)
			println(String.format(formatOut, answer, t + 1).trim())
			String.format(formatOut, answer, t + 1).trim()
		}
	}
	try {
		val nThreadsActual = min(nThreads, tests)
		if (nThreadsActual > 1) {
			val executor = Executors.newFixedThreadPool(nThreadsActual)
			val outputs = callables.map { executor.submit(it) }.map { it.get() }
			out.println(outputs.joinToString("\n"))
			executor.shutdown()
		} else {
			val startTime = System.currentTimeMillis()
			var prevTime = startTime
			for (t in callables.indices) {
				val output = callables[t].call()
				out.println(output)
				val curTime = System.currentTimeMillis()
				if (curTime > prevTime + 1000) {
					System.err.println("${((t + 1) * 100.0 / tests).toInt()}%\t${(curTime - startTime) / 1000.0}s")
					prevTime = curTime
				}
			}
		}
	} catch (e: Exception) {
		System.out.flush()
		System.err.flush()
		e.printStackTrace()
		exitProcess(1)
	}
	out.close()
	println("COMPLETE")
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
