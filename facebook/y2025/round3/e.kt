package facebook.y2025.round3

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private fun solve(s: String): List<List<Int>> {
	var sorted = true
	for (i in 1 until s.length) if (s[i - 1] > s[i]) sorted = false
	if (sorted) return listOf()
	if (s in precalc) {
		val (t, a) = precalc[s]!!
		return listOf(a) + solve(t)
	}
	val n = s.length / 2
	val count = s.groupBy { it }.mapValues { it.value.size }
	if (count['0']!! < count['1']!!) {
		val sReversed = s.reversed().map { (it.code xor 1).toChar() }.joinToString("")
		val ans = solve(sReversed)
		return ans.map { list -> list.map { 2 * n - 1 - it } }
	}
	if (s[0] == '0' && s[1] == '0') {
		val ans = solve(s.drop(2))
		return ans.map { list -> listOf(0) + list.map { it + 2 } }
	}
	val z0 = s.drop(2).indexOf('0') + 2
	val z1 = s.drop(z0 + 1).indexOf('0') + z0 + 1
	if (z1 < n) {
		val a = mutableListOf(0, 1)
		val b = mutableListOf(z0, z1)
		val used = BooleanArray(2 * n)
		used[0] = true
		used[1] = true
		used[z0] = true
		used[z1] = true
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
		val c = s.toCharArray()
		for (i in 0 until n) {
			val temp = c[a[i]]
			c[a[i]] = c[b[i]]
			c[b[i]] = temp
		}
		return listOf(a) + solve(String(c))
	}
	val a = (0 until n).toList()
	val b = (n until 2 * n).toList()
	val c = s.toCharArray()
	for (i in 0 until n) {
		val temp = c[a[i]]
		c[a[i]] = c[b[i]]
		c[b[i]] = temp
	}
	return listOf(a) + solve(String(c))
}

private data class InputE(val s: String)

private fun solve(input: InputE): String {
	val ans = solve(input.s)
	val n = input.s.length / 2
	val sb = StringBuilder()
	sb.append(ans.size)
	for (list in ans) {
		sb.append("\n").append(list.map { it + 1 }.sorted().joinToString(" "))
		sb.append("\n").append(((0 until 2 * n) - list.toSet()).map { it + 1 }.sorted().joinToString(" "))
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
	for (comb in Combinatorics.combinations(2 * n - 1, n - 1)) {
		val a = comb.toList().plus(2 * n - 1)
		val b = (0 until 2 * n) - a.toSet()
		val c = s.toCharArray()
		for (i in 0 until n) {
			val temp = c[a[i]]
			c[a[i]] = c[b[i]]
			c[b[i]] = temp
		}
		result[String(c)] = a
	}
	return result
}

val precalc = mutableMapOf<String, Pair<String, List<Int>>>()

fun precalc() {
	for (n in 3..6) {
		for (k in 0..2 * n) {
			val s = (List(2 * n - k) { "0" } + List(k) { "1" }).joinToString("")
			val seen = mutableSetOf(s)
			var layer = mutableSetOf(s)
			while (true) {
				val new = mutableSetOf<String>()
				for (v in layer) {
					for ((u, a) in nei(v)) {
						if (u in seen) continue
						precalc[u] = v to a
						new.add(u)
					}
				}
				if (new.isEmpty()) break
				seen.addAll(new)
				layer = new
			}
		}
	}
}

object Combinatorics {
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
