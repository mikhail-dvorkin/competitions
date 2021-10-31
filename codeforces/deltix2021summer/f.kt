package codeforces.deltix2021summer

fun main() {
	readLn()
	val strengths = readInts().map { it.toModular() }
	val n = strengths.size
	val probWin = Array(n) { i -> Array(n) { j -> strengths[i] / (strengths[i] + strengths[j]) } }
	val masks = 1 shl n
	val pScc = Array(masks) { 1.toModular() }
	var ans = 0.toModular()
	for (mask in 1 until masks) {
		if (mask.countOneBits() == 1) continue
		var top = (mask - 1) and mask
		var rest = 1L
		while (top > 0) {
			var thisTop = pScc[top]
			for (i in 0 until n) {
				if (!top.hasBit(i)) continue
				for (j in 0 until n) {
					if (top.hasBit(j) || !mask.hasBit(j)) continue
					thisTop *= probWin[i][j]
				}
			}
			if (mask == masks - 1) ans += top.countOneBits() * thisTop
			rest -= thisTop.x
			top = (top - 1) and mask
		}
		pScc[mask] = rest.toModular()
	}
	println(ans + n * pScc.last())
}

private fun Int.toModular() = Modular(this)//toDouble()
private fun Long.toModular() = Modular(this)
private class Modular {
	companion object {
		const val M = 1_000_000_007
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Long) { x = (value % M).toInt().let { if (it < 0) it + M else it } }
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

private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
