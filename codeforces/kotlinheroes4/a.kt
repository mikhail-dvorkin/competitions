package codeforces.kotlinheroes4

import kotlin.math.pow

fun main() = repeat(readInt()) {
	val (n, k) = readInts()
	val d = 4
	val c = List(d) { k.toDouble().pow(it).toInt() }
	val s = c.sum()
	println(c.map { it.toLong() * n / s }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
