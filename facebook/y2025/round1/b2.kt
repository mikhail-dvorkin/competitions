package facebook.y2025.round1

import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.system.exitProcess

private data class InputB2(val n: Long, val a: Long, val b: Long)

private fun solve(n: Long, m: Long): Modular {
	var ans = 1.toModularUnsafe()
	val ps = primeFactorization(m).groupBy { it }
	for (entry in ps) {
		val k = entry.value.size
//		ans *= cnk(n.toInt() - 1 + k, k)
		ans *= cnkSmallK(n - 1 + k, k)
	}
	return ans
}

private fun solve(input: InputB2): String {
	val (n, a, b) = input
	var ans = 0.toModularUnsafe()
	val divisorsOfB = divisorsOf(b)
	for (m in divisorsOfB) {
		if (m > a) break
		ans += solve(n, m) * solve(n, b / m)
	}
	return ans.toString()
}

private fun read(): InputB2 {
	val (n, a, b) = readLongs()
	return InputB2(n, a, b)
}

fun divisorsOf(n: Long): List<Long> {
	val divisors = mutableListOf<Long>()
	var i = 1L
	while (i * i <= n) {
		if (n % i == 0L) {
			divisors.add(i)
			if (i * i < n) divisors.add(n / i)
		}
		i++
	}
	divisors.sort()
	return divisors
}

fun primeFactorization(n: Long): List<Long> {
	val factors = mutableListOf<Long>()
	var n = n
	var i = 2L
	while (n > 1 && i * i <= n) {
		while (n % i == 0L) {
			factors.add(i)
			n /= i
		}
		i++
	}
	if (n > 1) factors.add(n)
	return factors
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
//typealias ModularArray = DoubleArray; val ModularArray.data; get() = this
private fun Double.inverse() = 1 / this

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class Modular(val x: Int) {
	companion object {
		const val M = 1_000_000_007; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inverse())
	inline fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	fun pow(p: Int): Modular = when {
		p < 0 -> this.inverse().pow(-p)
		p == 0 -> 1.toModularUnsafe()
		p == 1 -> this
		p % 2 != 0 -> this.times(this.pow(p - 1))
		else -> this.pow(p / 2).let { it.times(it) }
	}
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)
private fun Int.toModular() = Modular(if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M })
private fun Long.toModular() = Modular((if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M }).toInt())
private fun java.math.BigInteger.toModular() = Modular(mod(Modular.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = Modular(fold(0L) { acc, c -> (c - '0' + 10 * acc) % Modular.M }.toInt())

@JvmInline
private value class ModularArray(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: Modular) { data[index] = value.x }
	fun sum() = (data.sumOf { it.toLong() } % Modular.M).toInt().toModularUnsafe()
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular) = ModularArray(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)

private fun cnkSmallK(n: Long, k: Int): Modular {
	if (k == 0) return 1.toModularUnsafe()
	return cnkSmallK(n - 1, k - 1) * n.toModular() / k.toModular()
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
private fun readLongs() = readStrings().map { it.toLong() }
