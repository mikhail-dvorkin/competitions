package codeforces.kotlinheroes2.practice

import kotlin.math.pow
import kotlin.math.roundToLong

private fun f(n: Long): Int {
	if (n == 0L) return 0
	val ones = (10.0).pow(n.toString().length.toDouble()).roundToLong() / 9
	return ones.toInt()
}

fun main() = println(f(readLine()!!.toLong()))
