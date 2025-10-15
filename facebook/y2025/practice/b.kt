package facebook.y2025.practice

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private data class InputB(val f: List<BooleanArray>, val s: Int)

private fun solve(input: InputB): String {
	val (f, s) = input
	val hei = f.size; val wid = f[0].size
	val queue = mutableListOf<Pair<Int, Int>>()
	fun add(y: Int, x: Int) {
		queue.add(y to x)
	}
	for (y in 0 until hei) {
		add(y, -1)
		add(y, wid)
		for (x in 0 until wid) if (f[y][x]) add(y, x)
	}
	for (x in 0 until wid) {
		add(-1, x)
		add(hei, x)
	}
	val dist = List(hei) { IntArray(wid) }
	var low = 0
	while (low < queue.size) {
		val (vy, vx) = queue[low++]
		val vDist = dist.getOrNull(vy)?.getOrNull(vx) ?: 0
		for (d in 0 until 4) {
			val uy = vy + DY[d]; val ux = vx + DX[d]
			if (uy !in 0 until hei || ux !in 0 until wid || f[uy][ux] || dist[uy][ux] != 0) continue
			dist[uy][ux] = vDist + 1
			add(uy, ux)
		}
	}

	val mark = List(hei) { BooleanArray(wid) }

	var ans = 0
	fun dfs(vy: Int, vx: Int): Int {
		mark[vy][vx] = true
		var result = 1
		for (d in 0 until 4) {
			val uy = vy + DY[d]; val ux = vx + DX[d]
			if (uy !in 0 until hei || ux !in 0 until wid || mark[uy][ux] || dist[uy][ux] <= s) continue
			result += dfs(uy, ux)
		}
		return result
	}
	for (y in 0 until hei) for (x in 0 until wid) {
		if (mark[y][x] || dist[y][x] <= s) continue
		ans = maxOf(ans, dfs(y, x))
	}
	return ans.toString()
}

private fun read(): InputB {
	val (hei, _, s) = readInts()
	val f = List(hei) { readln().map { it == '#' }.toBooleanArray() }
	return InputB(f, s)
}

val DY = intArrayOf(0, 1, 0, -1)
val DX = intArrayOf(1, 0, -1, 0)

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
