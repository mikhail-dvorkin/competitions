package codeforces.kotlinheroes10.practice

import kotlin.math.abs

private fun solve() {
	val s = readln()
	val ans = (s[0] towards s.last()).flatMap { c ->
		s.indices.filter { s[it] == c }
	}
	println("${abs(s[0] - s.last())} ${ans.size}")
	println(ans.map { it + 1 }.joinToString(" "))
}

private infix fun Char.towards(to: Char) = if (to > this) this..to else this downTo to

private fun readInt() = readln().toInt()

fun main() = repeat(readInt()) { solve() }
