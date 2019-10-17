package codeforces.round586

import kotlin.math.sqrt

fun main() {
	val n = readInt()
	val a = List(n) { readInts() }
	val b = List(n) { i ->
		val j = (i + 1) % n
		val k = (i + 2) % n
		sqrt(a[i][j].toLong() * a[i][k] / a[j][k] * 1.0).toInt()
	}
	println(b.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
