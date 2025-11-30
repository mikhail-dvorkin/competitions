package facebook.y2025.round3

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private data class InputB(val a: List<Int>, val b: List<Int>)

private fun solve(input: InputB): Long {
	val (aIn, b) = input
	val a = aIn.map { it.toLong() }
	val n = a.size
	val aPref = a.scan(0, Long::plus)
	val temp = IntArray(n + 1)
	val dp = List(n + 1) { i -> LongArray(i + 1) { aPref[i] } }
	dp[0][0] = 0
	for (i in 0 until n) {
		for (len in b[i]..i) {
			dp[i + 1][len] = dp[i][len]
		}
		temp.fill(0)
		for (j in 0..i) {
			val pos = i + b[j] - j - 1
			if (pos >= 0) temp[pos] = maxOf(temp[pos], b[j])
		}
		var need = 0
		for (len in i downTo 0) {
			dp[i + 1][len] = minOf(dp[i + 1][len], dp[i + 1][len + 1])
			need = maxOf(need, temp[len])
//			for (j in i + 1 - len..i) if (len <= i + b[j] - j - 1) need = maxOf(need, b[j])
			if (need > i - len) continue
			dp[i + 1][len] = minOf(dp[i + 1][len], dp[i - len][need] + aPref[i + 1] - aPref[i + 1 - len])
		}
	}
	return dp[n][0]
}

private fun read(): InputB {
	readInt()
	val a = readInts()
	val b = readInts()
	return InputB(a, b)
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
