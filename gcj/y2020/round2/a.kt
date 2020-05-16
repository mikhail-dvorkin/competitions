package gcj.y2020.round2

import kotlin.math.pow

fun solvePancakes(aIn: Long, bIn: Long): String {
	var a = aIn; var b = bIn
	var i = 1L
	val maxN = (a.toDouble() + b + 100).pow(0.5).toLong() * 2

	fun step(): String? {
		if (maxOf(a, b) < i) return "${i - 1} $a $b"
		if (a >= b) a -= i else b -= i
		i++
		return null
	}

	fun take(amount: Long) = (i - 1..maxN).binarySearch { j -> sum(i, j) >= amount } - 1

	val takeA = take(a - b)
	a -= sum(i, takeA)
	i = takeA + 1

	step()?.also { return it }

	val takeB = take(b - a)
	b -= sum(i, takeB)
	i = takeB + 1

	if (b > a) { step()?.also { return it } }

	val takeAB = (take(a + b) - i) / 2
	a -= (i + takeAB - 1) * takeAB
	b -= (i + takeAB) * takeAB
	i += 2 * takeAB

	while (true) { step()?.also { return it } }
}

private fun solve(): String {
	val (a, b) = readLongs()
	return solvePancakes(a, b)
}

private fun sum(start: Long, end: Long) = (start + end) * (end + 1 - start) / 2

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun LongRange.binarySearch(predicate: (Long) -> Boolean): Long {
	var (low, high) = this.first to this.last // must be false .. must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
