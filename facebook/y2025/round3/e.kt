package facebook.y2025.round3

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.system.exitProcess

private fun solve(s: String): List<List<Int>> {
	val sorted = s.zipWithNext().all { it.first <= it.second }
	if (sorted) return listOf()

	if (s in precalc) {
		val (prev, a) = precalc[s]!!
		return listOf(a) + solve(prev)
	}

	val n = s.length / 2

	if (s.count { it == '0' } < s.count { it == '1' }) {
		val sReversed = s.reversed().map { (it.code xor 1).toChar() }.joinToString("")
		val ans = solve(sReversed)
		return ans.map { list -> list.map { 2 * n - 1 - it }.reversed() }
	}

	if (s.startsWith("00")) {
		val ans = solve(s.drop(2))
		return ans.map { list -> listOf(0) + list.map { it + 2 } }
	}

	val z0 = s.indexOf('0', 2)
	val z1 = s.indexOf('0', z0 + 1)
	val a = if (z1 >= n) {
		(0 until n).toList()
	} else {
		val a = mutableListOf(0, 1)
		val b = mutableListOf(z0, z1)
		val used = BooleanArray(2 * n) { it in a + b }
		while (a.size < n) {
			var i = a.last() + 1
			while (used[i]) i++
			used[i] = true
			var j = b.last() + 1
			while (used[j]) j++
			used[j] = true
			a.add(i)
			b.add(j)
		}
		a
	}
	return listOf(a) + solve(apply(s, a))
}

private data class InputE(val s: String)

private fun solve(input: InputE): String {
	val ans = solve(input.s)
	val n = input.s.length / 2
	val sb = StringBuilder()
	sb.append(ans.size)
	for (list in ans) {
		sb.append("\n").append(list.map { it + 1 }.joinToString(" "))
		sb.append("\n").append(((0 until 2 * n) - list.toSet()).map { it + 1 }.joinToString(" "))
	}
	return sb.toString()
}

private fun read(): InputE {
	readInt()
	return InputE(readln())
}

fun nei(s: String): Map<String, List<Int>> {
	val n = s.length / 2
	val result = mutableMapOf<String, List<Int>>()
	for (aArray in combinations(2 * n - 1, n)) {
		val a = aArray.toList()
		result[apply(s, a)] = a
	}
	return result
}

fun apply(s: String, a: List<Int>): String {
	val b = s.indices - a.toSet()
	val c = s.toCharArray()
	for (i in a.indices) {
		val temp = c[a[i]]
		c[a[i]] = c[b[i]]
		c[b[i]] = temp
	}
	return String(c)
}

val precalc = mutableMapOf<String, Pair<String, List<Int>>>()

fun precalc() {
	for (n in 3..4) for (k in 0..n) {
		val s = (List(2 * n - k) { "0" } + List(k) { "1" }).joinToString("")
		val seen = mutableSetOf(s)
		var layer = mutableSetOf(s)
		while (true) {
			val newLayer = mutableSetOf<String>()
			for (v in layer) for ((u, a) in nei(v)) {
				if (u in seen) continue
				precalc[u] = v to a
				newLayer.add(u)
			}
			if (newLayer.isEmpty()) break
			seen.addAll(newLayer)
			layer = newLayer
		}
	}
}

fun combinations(n: Int, k: Int): Iterable<IntArray> {
	require(!(k > n || n < 0 || k < 0)) { "n = $n, k = $k" }
	if (k == 0) {
		return mutableSetOf(IntArray(0))
	}
	return CombinationsIterable(n, k)
}

internal class CombinationsIterable(var n: Int, var k: Int) : Iterable<IntArray> {
	override fun iterator(): MutableIterator<IntArray> {
		return CombinationsIterator()
	}

	internal inner class CombinationsIterator : MutableIterator<IntArray> {
		var a = IntArray(k)

		init {
			for (i in 0..<k) {
				a[i] = i
			}
			a[k - 1]--
		}

		override fun hasNext(): Boolean {
			for (i in k - 1 downTo 0) {
				if (a[i] != n - k + i) {
					return true
				}
			}
			return false
		}

		override fun next(): IntArray {
			var i = k - 1
			while (i >= 0 && a[i] == n - k + i) {
				i--
			}
			if (i < 0) {
				throw NoSuchElementException()
			}
			a[i]++
			i++
			while (i < k) {
				a[i] = a[i - 1] + 1
				i++
			}
			return a.clone()
		}

		override fun remove() {
			throw UnsupportedOperationException()
		}
	}
}

fun main() {
	precalc()
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
		val nThreadsActual = minOf(nThreads, tests)
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
