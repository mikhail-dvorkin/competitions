package gcj.y2022.round1b

import kotlin.math.abs

private fun solve(): Long {
	val (n, _) = readInts()
	val lists = List(n) { readInts() }
	var pLow = 0
	var pHigh = 0
	var costLow = 0L
	var costHigh = 0L
	for (list in lists) {
		val pLowNew = list.minOrNull()!!
		val pHighNew = list.maxOrNull()!!
		val costLowNew = minOf(costLow + abs(pLow - pHighNew), costHigh + abs(pHigh - pHighNew)) + pHighNew - pLowNew
		val costHighNew = minOf(costLow + abs(pLow - pLowNew), costHigh + abs(pHigh - pLowNew)) + pHighNew - pLowNew
		pLow = pLowNew; pHigh = pHighNew; costLow = costLowNew; costHigh = costHighNew
	}
	return minOf(costLow, costHigh)
}

fun main() {
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
