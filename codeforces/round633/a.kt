package codeforces.round633

@ExperimentalStdlibApi
fun main() = repeat(readInt()) {
	readLn()
	val a = readInts()
	val maxUp = a.scanReduce(::maxOf).zip(a, Int::minus).max()!!
	println(Int.SIZE_BITS - maxUp.countLeadingZeroBits())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
