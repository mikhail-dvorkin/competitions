package codeforces.kotlinheroes3

import kotlin.math.abs

private fun solve() {
	readLn()
	val a = readInts()
	val x = a.indices.filter { a[it] != 0 }.minByOrNull { abs(a[it]) }!!
	val take = a.indices.map { (a[it] > 0) xor (it == x) }
	println(a.indices.filter { take[it] }.sumOf { a[it] })
	println(take.joinToString("") { if (it) "1" else "0" })
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
