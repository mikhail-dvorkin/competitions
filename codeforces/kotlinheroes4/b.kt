package codeforces.kotlinheroes4

@ExperimentalStdlibApi
fun main() = repeat(readInt()) {
	val (_, day1, day2) = readInts()
	println(readLn().scan(0) { prev, c -> if (c == '0') 0 else minOf(day1, day2 - prev) }.sum())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
