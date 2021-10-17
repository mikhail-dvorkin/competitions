package atcoder.arc128

fun main() {
	readLn()
	val a = readInts()
	val cumSum = mutableListOf(0.toModular())
	fun sum(from: Int, to: Int) = cumSum[maxOf(to, from)] - cumSum[from]
	var same = 0
	var diff = 0
	var ans = 1.toModular()
	for (i in a.indices) {
		if (i < 2 || a[i] != a[i - 2]) diff = i
		if (i < 1 || a[i] == a[i - 1]) {
			same = i
		} else {
			ans = sum(same, i) - sum(maxOf(diff - 1, same), i - 2)
		}
		cumSum.add(cumSum.last() + ans)
	}
	println(ans)
}

fun Int.toModular() = Modular(this)
class Modular {
	companion object {
		const val M = 998244353
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular) = Modular((x + that.x) % M)
	operator fun minus(that: Modular) = Modular((x + M - that.x) % M)
	operator fun times(that: Modular) = (x.toLong() * that.x % M).toInt().toModular()
	fun modInverse() = Modular(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular) = times(that.modInverse())
	override fun toString() = x.toString()
}
operator fun Int.plus(that: Modular) = Modular(this) + that
operator fun Int.minus(that: Modular) = Modular(this) - that
operator fun Int.times(that: Modular) = Modular(this) * that
operator fun Int.div(that: Modular) = Modular(this) / that

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
