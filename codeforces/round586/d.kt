package codeforces.round586

import java.lang.Long

fun main() {
	readLn()
	val a = readStrings().map { it.toLong() }
	val save = a.groupBy { Long.numberOfTrailingZeros(it) }.maxBy { it.value.size }!!.value
	val remove = a.minus(save)
	println(remove.size)
	println(remove.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
