package codeforces.kotlinheroes10.practice

import kotlin.math.sign

fun parseSize(s: String): Int {
	if (s == "M") return 0
	return s.length * (if (s.endsWith("L")) 1 else -1)
}

private fun solve() {
	val (a, b) = readStrings().map { parseSize(it) }
	println("<=>"[(a - b).sign + 1])
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")

fun main() = repeat(readInt()) { solve() }
