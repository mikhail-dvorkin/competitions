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
	return if (v + h < n) -1 else minOf(solveVertical(x, y, v), solveVertical(y, x, h))
}

fun solveVertical(x: IntArray, y: IntArray, v: Int): Int {
	val n = x.size
	val h = maxOf(n - v, 0)
	var ans = y.max()!! + if (v < n) x.max()!! else 0
	val rows = y.zip(x).groupBy({ it.first }, { it.second }).asSequence().sortedByDescending { it.key }
	val xAffordable = if (h == 0) 0 else x.sorted()[h - 1]
	var xMaxAbove = 0
	var countAbove = 0
	for ((yMax, xs) in rows) {
		val xMax = xs.max()!!
		if (xMaxAbove <= xMax) {
			ans = minOf(ans, yMax + maxOf(xAffordable, xMaxAbove))
			xMaxAbove = xMax
		}
		countAbove += xs.size
		if (countAbove > h) { break }
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
