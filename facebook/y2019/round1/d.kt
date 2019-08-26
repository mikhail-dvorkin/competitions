package facebook.y2019.round1

private fun solve(): Int {
	val (n, h, v) = readInts()
	val (x, y) = List(2) {
		val (x1, x2, ax, bx, cx, dx) = readInts()
		val x = IntArray(n)
		x[0] = x1
		x[1] = x2
		for (i in 2 until n) {
			x[i] = ((ax * 1L * x[i - 2] + bx * 1L * x[i - 1] + cx) % dx + 1).toInt()
		}
		x
	}
	val sorted = listOf(x to y, y to x).map { (x, y) ->
		val longs = List(n) { (x[it].toLong() shl 32) + y[it].toLong() }.sorted()
		longs.map { (it shr 32).toInt() } to longs.map { it.toInt() }
	}
	return if (h + v < n) -1 else List(2) { t ->
		solveVertical(sorted[t].second, sorted[t].first, sorted[1 - t].first, listOf(h, v)[t])
	}.min()!!
}

fun solveVertical(x: List<Int>, y: List<Int>, xSorted: List<Int>, v: Int): Int {
	val n = x.size
	var ans = y.last() + if (v < n) xSorted.last() else 0
	val xAffordable = xSorted.getOrElse(n - v - 1) { 0 }
	var xMaxAbove = 0
	var xMax = 0
	for (i in n - 1 downTo 0) {
		xMax = maxOf(xMax, x[i])
		if (i > 0 && y[i] == y[i - 1]) continue
		if (xMaxAbove <= xMax) {
			ans = minOf(ans, y[i] + maxOf(xAffordable, xMaxAbove))
			xMaxAbove = xMax
		}
		if (i < v) break
		xMax = 0
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private operator fun <T> List<T>.component6(): T { return get(5) }

private val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
@Suppress("unused")
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
