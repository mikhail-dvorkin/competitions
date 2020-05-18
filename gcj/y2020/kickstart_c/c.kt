package gcj.y2020.kickstart_c

import kotlin.math.pow

private fun solve(): Long {
	readLn()
	val pref = mutableMapOf(0 to 1)
	var prefSum = 0; var minPrefSum = 0
	return readInts().map { x ->
		prefSum += x
		pref[prefSum] = (pref[prefSum] ?: 0) + 1
		minPrefSum = minOf(minPrefSum, prefSum)
		(0..(prefSum - minPrefSum).sqrtFloor()).sumBy { pref[prefSum - it * it] ?: 0 } - 1L
	}.sum()
}

private fun Int.sqrtFloor() = toDouble().pow(0.5).toInt()

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
