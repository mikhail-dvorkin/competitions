package atcoder.arc154

fun main() {
	readLn()
	val a = readLn()
	val b = readLn()
	var aa = 0.toModular()
	var bb = 0.toModular()
	for (i in a.indices) {
		val digits = listOf(a[i], b[i]).map { it - '0' }.sorted()
		aa = digits[0] + 10 * aa
		bb = digits[1] + 10 * bb
	}
	println((aa * bb).x)
}

private fun Int.toModular() = Modular(this)//toDouble(); typealias Modular = Double
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
