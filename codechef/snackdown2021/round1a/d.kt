package codechef.snackdown2021.round1a

import kotlin.math.abs

private fun solve(): Int {
	readLn()
	val a = readInts().sorted()
	if (a.size == 2) return 0
	var ans = minOf(makeEqual(a.drop(1)), makeEqual(a.dropLast(1))).coerceInInt()
	var i = 1
	var j = a.lastIndex - 1
	val borders = a[0] + a.last()
	while (i < j) {
		ans = minOf(ans, abs(a[i] + a[j] - borders))
		if (a[i] + a[j] < borders) i++ else j--
	}
	return ans
}

private fun makeEqual(a: List<Int>): Long {
	val prefixSums = a.runningFold(0L, Long::plus)
	fun sum(from: Int, to: Int) = prefixSums[to] - prefixSums[from]
	return a.indices.minOf { a[it] * (2L * it - a.size) + sum(it, a.size) - sum(0, it) }
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve()) }

private fun Long.coerceInInt() = if (this >= Int.MAX_VALUE) Int.MAX_VALUE else if (this <= Int.MIN_VALUE) Int.MIN_VALUE else toInt()

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
