package facebook.y2025.practice

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private data class InputC(val a: List<Int>)

private fun solve(input: InputC): String {
	val a = input.a

	val memo = IntArray(a.size + 1) { -1 }
	fun possible(x: Int): Int {
		if (memo[x] == -1) memo[x] = run {
			if (x == 0) return@run 0
			val p = a[x - 1]
			if (x - p - 1 < 0) return@run -1
			for (i in p - 1 downTo 0) {
				if (a[x - p - 1 + i] != i) return@run -1
			}
			if (possible(x - p - 1) >= 0) return@run x - p - 1
			val q = a[x - p - 2]
			if (q <= p) return@run -1
			for (i in q - 1 downTo p + 1) {
				if (a[x - p - 2 - q + i] != i) return@run -1
			}
			if (possible(x - q - 1) >= 0) return@run x - q - 1
			return@run -1
		}
		return memo[x]
	}
	require(possible(a.size) >= 0)

	val ans = mutableListOf<String>()
	fun solve(n: Int, shifts: Int) {
		if (n == 0) return
		val m = memo[n]
		val z = (m until n).first { a[it] == 0 }
		val shiftsHere = (n - z - shifts) mod (n - m)
		solve(m, shifts + shiftsHere)
		ans.add("1 ${n - m}")
		repeat(shiftsHere) { ans.add("2") }
	}
	solve(a.size, 0)

	return "${ans.size}\n${ans.joinToString("\n")}"
}

private fun read(): InputC {
	readln()
	return InputC(readInts().map { it - 1 })
}

private infix fun Int.mod(other: Int) = (this % other).let { it + (other and (((it xor other) and (it or -it)) shr 31)) }

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
