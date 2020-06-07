package codeforces.kotlinheroes4

@ExperimentalStdlibApi
fun main() = repeat(readInt()) {
	val (n, k, x, y) = readInts()
	val a = readInts().sorted()
	val prefixSums = a.scan(0L, Long::plus)
	val take = prefixSums.indices.last { prefixSums[it] <= k.toLong() * n }
	val withY = (n - take) * x.toLong() + y
	val withoutY = a.count { it > k } * x.toLong()
	println(minOf(withY, withoutY))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
