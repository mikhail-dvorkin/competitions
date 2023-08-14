package codeforces.vk2022.round1

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private fun solve() {
	val (h, w, k) = readInts()
	val zero = 0.toModular()
	val one = 1.toModular()
	val broken = List(k) { readInts().map { it - 1 } }
	val powerOfTwo = Array(h * w + 1) { one }
	for (i in 1 until powerOfTwo.size) {
		powerOfTwo[i] = 2 * powerOfTwo[i - 1]
	}
	var ans = 0.toModular()
	for (hei in 1..h) for (wid in 1..w) {
		for (y0 in 0 until hei) for (y1 in 0 until hei) for (x0 in 0 until wid) for (x1 in 0 until wid) {
			if ((x0 == 0) xor (y0 == 0)) continue
			if ((x1 == wid - 1) xor (y1 == hei - 1)) continue
//			if ((x0 == wid - 1) && (y1 != 0)) continue
//			if ((y0 == hei - 1) && (x1 != 0)) continue
			val emptyByBorders = (if (y0 == 0) 0 else (y0 + x0 - 1)) + (if (y1 == hei - 1) 0 else (hei - y1 + wid - x1 - 3))
			val borderImportant = setOf(0 to x0, y0 to 0, hei - 1 to x1, y1 to wid - 1)
			val variable = hei * wid - emptyByBorders - borderImportant.size
//			println("$hei $wid $y0 $y1 $x0 $x1 -> $variable")
//			ans += powerOfTwo[variable]
		}
	}
	println(ans)
}

@OptIn(ExperimentalTime::class)
fun main() {
	measureTime {
		repeat(readInt()) { solve() }
	}.also { System.err.println(it) }
}

//private fun Int.toModular() = toDouble(); typealias Modular = Double
private fun Int.toModular() = Modular1(this)//toDouble()
private class Modular1 {
	companion object {
		const val M = 998244353
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular1) = Modular1((x + that.x) % M)
	operator fun minus(that: Modular1) = Modular1((x + M - that.x) % M)
	operator fun times(that: Modular1) = (x.toLong() * that.x % M).toInt().toModular()
	private fun modInverse() = Modular1(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular1) = times(that.modInverse())
	override fun toString() = x.toString()
}
private operator fun Int.plus(that: Modular1) = Modular1(this) + that
private operator fun Int.minus(that: Modular1) = Modular1(this) - that
private operator fun Int.times(that: Modular1) = Modular1(this) * that
private operator fun Int.div(that: Modular1) = Modular1(this) / that

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
