package atcoder.arc145

fun main() {
	val n = readInt()
	fun factorial(n: Int): Modular {
		var res = 1.toModular()
		for (i in 2..n) res = i * res
		return res
	}
	var ans = factorial(2 * n) / factorial(n + 1)
	repeat(n) { ans = 2 * ans }
	println(ans)
}

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

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
