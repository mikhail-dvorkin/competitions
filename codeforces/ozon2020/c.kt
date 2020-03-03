package codeforces.ozon2020

import kotlin.math.abs

fun main() {
	val (_, m) = readInts()
	val count = MutableList(m) { 0 }
	val a = readInts()
	for (x in a) {
		count[x % m]++
	}
	if (count.any { it >= 2 }) return println(0)
	var ans = 1L
	for (i in a.indices) {
		for (j in 0 until i) {
			ans = ans * abs(a[i] - a[j]) % m
		}
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
