package codeforces.round740

fun main() {
	val (n, m) = readInts()
	Modular.M = m
	val primeDivisors = primeDivisors(n)
	val a = Array(n + 1) { it.toModular() }
	for (i in 3..n) {
		a[i] = 1 + 2 * a[i - 1]
		for (r in divisorsOf(i, primeDivisors)) {
			if (r == 1 || r == i) continue
			a[i] += a[r] - a[r - 1]
		}
	}
	println(a.last())
}

fun divisorsOf(n: Int, primeDivisors: IntArray): IntArray {
	if (n == 1) return intArrayOf(1)
	val p = primeDivisors[n]
	var m = n / p
	var counter = 2
	while (m % p == 0) {
		m /= p
		counter++
	}
	val divisorsOfM = divisorsOf(m, primeDivisors)
	val result = IntArray(divisorsOfM.size * counter)
	for (i in divisorsOfM.indices) {
		var d = divisorsOfM[i]
		for (j in 0 until counter) {
			result[i * counter + j] = d
			d *= p
		}
	}
	return result
}

fun primeDivisors(n: Int): IntArray {
	val primeDivisors = IntArray(n + 1) { it }
	for (i in 2..n) {
		if (primeDivisors[i] < i) continue
		var j = i * i
		if (j > n) break
		do {
			primeDivisors[j] = i
			j += i
		} while (j <= n)
	}
	return primeDivisors
}

private fun Int.toModular() = Modular(this)//toDouble()
private class Modular {
	companion object {
		var M: Int = 0
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular) = Modular((x + that.x) % M)
	operator fun minus(that: Modular) = Modular((x + M - that.x) % M)
	operator fun times(that: Modular) = (x.toLong() * that.x % M).toInt().toModular()
	private fun modInverse() = Modular(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular) = times(that.modInverse())
	override fun toString() = x.toString()
}
private operator fun Int.plus(that: Modular) = Modular(this) + that
private operator fun Int.minus(that: Modular) = Modular(this) - that
private operator fun Int.times(that: Modular) = Modular(this) * that
private operator fun Int.div(that: Modular) = Modular(this) / that

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
