package codeforces.round586

import kotlin.math.*

fun main() {
	val n = readInt()
	val a = List(n) { readInts() }
	println(List(n) { i ->
		val j = (i + 1) % n
		val k = (i + 2) % n
		sqrt(1.0 * a[i][j] * a[i][k] / a[j][k]).roundToInt()
	}.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
