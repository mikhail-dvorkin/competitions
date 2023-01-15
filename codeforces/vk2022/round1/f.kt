package codeforces.vk2022.round1

fun main() {
	val (n, qIn) = readInts()
	val pOpenClose = qIn / 10_000.toModular()
	val one = 1.toModular()
	val pCloseOpen = one - pOpenClose

	val cnk = List(n + 1) { i -> Array(i + 1) { one } }
	for (i in cnk.indices) {
		for (j in 1 until i) {
			cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j]
		}
	}

	val fact2 = Array(n + 1) { one }
	fun fact2(i: Int) = fact2[i]
	for (i in 1..n) {
		fact2[i] = ((2 * i - 1) * fact2(i - 1))
	}
	val pPaired = List(n + 1) { i -> Array(i) { one } }
	for (i in 0..n) {
		for (k in 0 until i) {
			val kk = i - 1 - k
			pPaired[i][k] = ((cnk[i][k + 1] * fact2(k) * fact2(kk)) / fact2(i))
		}
	}

	val pAtLeast = List(n + 1) { i -> Array(i) { one } }
	fun pAtLeast(i: Int, j: Int): Modular {
		if (j >= i) return one
		return pAtLeast[i][j]
	}
	for (i in 0..n) {
		for (j in 0 until i) {
			if (i + j > n) continue
			var pij = 0.toModular()
			for (k in 0 until i) {
				val kk = i - 1 - k
				var pBalance = pAtLeast(k, j + 1) * pOpenClose
				if (j >= 1) {
					pBalance = (pAtLeast(k, j - 1) * pCloseOpen) + pBalance
				}
				pij = pPaired[i][k] * pAtLeast(kk, j) * pBalance + pij
			}
			pAtLeast[i][j] = pij
		}
	}
	println(pAtLeast(n, 0))
}

//private fun Int.toModular() = toDouble(); typealias Modular = Double
private fun Int.toModular() = Modular(this)//toDouble()
private class Modular {
	companion object {
		const val M = 998244353
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

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

