package codechef.snackdown2021.preelim

import java.util.*

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) {
	readLn()
	val a = readInts()
	val prev = IntArray(a.size)
	val treeMap = TreeMap<Int, Int>()
	for (i in a.indices) {
		val entry = treeMap.ceilingEntry(a[i])
		if (entry == null) {
			prev[i] = -1
		} else {
			prev[i] = entry.value
		}
		treeMap[a[i]] = i
	}
	val dp = MutableList(a.size) { 1.toModular() }
	var ans = 1.toModular()
	for (i in a.indices.reversed()) {
		dp[i] = 1 + dp[i]
		if (prev[i] == -1) {
			ans *= dp[i]
			continue
		}
		dp[prev[i]] *= dp[i]
	}
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

private fun readLn() = readLine()!!.trim()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
