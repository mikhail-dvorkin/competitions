package codeforces.kotlinheroes4

import kotlin.math.pow

const val D = 4

fun main() = repeat(readInt()) {
	val (n, k) = readInts()
	val c = List(D) { k.toDouble().pow(it).toLong() }
	val s = c.sum()
	println(c.map { it * n / s }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
