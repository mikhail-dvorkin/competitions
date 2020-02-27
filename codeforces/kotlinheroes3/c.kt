package codeforces.kotlinheroes3

import kotlin.math.absoluteValue

private fun solve() {
	readInt()
	val a = readInts()
	val take = a.indices.map { a[it] > 0 }.toMutableList()
	val x = a.indices.filter { a[it] != 0 }.minBy { a[it].absoluteValue }!!
	take[x] = !take[x]
	println(a.filterIndexed { index, _ -> take[index] }.sum())
	println(take.joinToString("") { if (it) "1" else "0" })
}

fun main() = repeat(readInt()) { solve() }


private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
