package codeforces.kotlinheroes11.practice

import kotlin.math.roundToInt
import kotlin.math.sqrt

private fun solve(): Boolean {
	readln()
	val s = readln()
	val m = sqrt(s.length.toDouble()).roundToInt()
	val t = buildString {
		for (i in 0 until m) for (j in 0 until m) {
			append((i in 1..m - 2 && j in 1..m - 2).iif(0, 1))
		}
	}
	return s == t
}

fun main() = repeat(readInt()) { println(solve().iif("Yes", "No")) }

private fun readInt() = readln().toInt()
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
