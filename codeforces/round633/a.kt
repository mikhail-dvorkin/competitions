package codeforces.round633

fun main() = repeat(readInt()) {
	readLn()
	val a = readInts()
	val maxUp = a.runningReduce(::maxOf).zip(a, Int::minus).maxOrNull()!!
	println(Int.SIZE_BITS - maxUp.countLeadingZeroBits())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
