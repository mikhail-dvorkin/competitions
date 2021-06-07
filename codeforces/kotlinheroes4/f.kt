package codeforces.kotlinheroes4

@ExperimentalStdlibApi
fun main() {
	val (hei, wid, q) = readInts()
	val a = List(hei) { readInts().toIntArray() }
	val rows = LongArray(hei) { a[it].sumLong() }
	val cols = LongArray(wid) { a.map { row -> row[it] }.toIntArray().sumLong() }
	val ans = List(q + 1) { i ->
		if (i > 0) {
			val (xIn, yIn, z) = readInts()
			val x = xIn - 1; val y = yIn - 1
			val prev = a[x][y].toLong()
			rows[x] += z - prev; cols[y] += z - prev
			a[x][y] = z
		}
		solve(rows) + solve(cols)
	}
	println(ans.joinToString(" "))
}

@ExperimentalStdlibApi
private fun solve(a: LongArray): Long {
	val prefixSums = a.scan(0L, Long::plus)
	val costLeft = a.mapIndexed { index, x -> x * (index + 1) }.sum()
	val costs = prefixSums.scan(costLeft) { cost, pref -> cost + 2 * pref - prefixSums.last() }
	return costs.minOrNull()!!
}

private fun IntArray.sumLong() = fold(0L, Long::plus)
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
