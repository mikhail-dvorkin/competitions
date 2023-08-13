package codechef.lunchtime2022_08

import kotlin.math.absoluteValue

private fun solve() {
	readLn()
	val a = readInts()
	var good2 = 0L
	var good1 = 0L
	var good0 = 0L
	var penalty = 0L
	for (i in a.indices.reversed()) {
		var x = a[i].toLong()
		val make2 = good2
		x -= make2
		penalty -= make2 * 2
		val make1 = maxOf(0, minOf(good1, x))
		x -= make1
		penalty -= make1
		val make0 = maxOf(0, minOf(good0, x))
		x -= make0
		good2 = if (x > 0) x / 2 else 0
		good1 = 0
		good0 = (if (x > 0 && x % 2 == 1L) 1 else 0)
		penalty += x.absoluteValue
	}
	println(penalty)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
