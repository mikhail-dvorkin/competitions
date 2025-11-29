package facebook.y2025.round3

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private fun solve(n: Int, k: Int): String {
	val area = n * (n + 1) / 2
	val partsTheory = if (k != 2) area ceilDiv k else {
		(1..n).sumOf { it ceilDiv 2 }
	}
	fun id(y: Int, x: Int) = y * n + x
	val nei = List(n * n) { mutableListOf<Int>() }
	val alive = mutableSetOf<Int>()
	for (y in 0 until n) for (x in 0 until n) if (x + y < n) {
		val v = id(y, x)
		alive.add(v)
		for (d in 0 until 4) {
			val yy = y + DY[d]
			val xx = x + DX[d]
			if (yy < 0 || xx < 0 || xx + yy >= n) continue
			val u = id(yy, xx)
			nei[v].add(u)
		}
	}
	val nei2 = nei.map { it.toList() }
	fun remove(v: Int) {
		alive.remove(v)
		for (u in nei[v]) {
			nei[u].remove(v)
		}
	}

val parts = mutableListOf<List<Int>>()
while (alive.isNotEmpty()) {
	val vInit = alive.minBy { nei[it].size }
	val comp = mutableListOf(vInit)
	remove(vInit)
	while (comp.size < k) {
		val candidates = comp.flatMap { nei[it] }.toSet().intersect(alive)
		if (candidates.isEmpty()) break
		val v = candidates.minBy { nei[it].size * n + it / n + it % n }
		comp.add(v)
		remove(v)
	}
	parts.add(comp)
}
	if (partsTheory != parts.size) {
		System.err.println("$n $k $partsTheory to ${parts.size}")
	}
	val where = IntArray(n * n) { -1 }
	for (i in parts.indices) {
		for (v in parts[i]) where[v] = i
	}
	val color = IntArray(parts.size) { -1 }
	for (i in parts.indices) {
		val neiColors = mutableSetOf<Int>()
		for (v in parts[i]) for (u in nei2[v]) {
			neiColors.add(color[where[u]])
		}
		color[i] = (0 until 26).first { it !in neiColors }
	}
	val sb = StringBuilder()
	sb.append(parts.size)
	for (y in n - 1 downTo 0) {
		sb.append("\n")
		for (x in 0 until n) if (x + y < n) {
			sb.append('a' + color[where[id(y, x)]])
		}
	}
	return sb.toString()
}

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

private data class InputA(val n: Int, val k: Int)

private fun solve(input: InputA): String {
	val (n, k) = input
	return solve(n, k)
}

private infix fun Int.ceilDiv(other: Int) = (this + other - 1) / other

private fun read(): InputA {
	val (n, k) = readInts()
	return InputA(n, k)
}

fun research() {
	for (n in 1..100) {
		print("$n ")
		for (k in 1..minOf(n * (n + 1) / 2, 2 * n)) {
			solve(n, k)
		}
	}
}

fun main() {
	research()
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
