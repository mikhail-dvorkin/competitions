package codeforces.kotlinheroes3

import kotlin.math.abs

private val options = (0..999).associateWith { "$it" } +
		(1..999).associate { it * 1000 to "$it" + "K" } +
		(1..2000).associate { it * 1000_000 to "$it" + "M" }

private fun solve() {
	val n = readInt()
	println(options.minWith(compareBy({ abs(it.key - n) }, { -it.key }))!!.value)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
