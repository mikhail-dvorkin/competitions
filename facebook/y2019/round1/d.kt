package facebook.y2019.round1

private fun solve(): Int {
	val (n, h, v) = readInts()
	val (x1, x2, ax, bx, cx, dx) = readInts()
	val (y1, y2, ay, by, cy, dy) = readInts()
	if (v + h < n) { return -1 }
	val x = IntArray(n)
	val y = IntArray(n)
	x[0] = x1
	x[1] = x2
	y[0] = y1
	y[1] = y2
	for (i in 2 until n) {
		x[i] = ((ax * 1L * x[i - 2] + bx * 1L * x[i - 1] + cx) % dx + 1).toInt()
		y[i] = ((ay * 1L * y[i - 2] + by * 1L * y[i - 1] + cy) % dy + 1).toInt()
	}

	var ans = x.max()!! + y.max()!!
	if (h >= n) ans = ans.coerceAtMost(x.max()!!)
	if (v >= n) ans = ans.coerceAtMost(y.max()!!)
	ans = ans.coerceAtMost(solveVertical(x, y, h, v))
	ans = ans.coerceAtMost(solveVertical(y, x, v, h))
	return ans
}

fun solveVertical(x: IntArray, y: IntArray, h: Int, v: Int): Int {
	val n = x.size
	var ans = x.max()!! + y.max()!!
	val xSorted = x.sorted()
	val yToPs = y.zip(x).groupBy { it.first }.toSortedMap()
	var xAboveMax = 0
	var countAbove = 0
	val hh = maxOf(n - v, 0)
	for (yMax in yToPs.keys.reversed()) {
		val ps = yToPs[yMax]!!
		val xs = ps.map { it.second }.sorted()
		if (xAboveMax <= xs.last()) {
			if (countAbove <= hh) {
				val xNumberHH1 = if (hh == 0) 0 else xSorted[hh - 1]
				if (xNumberHH1 >= xAboveMax) {
					ans = ans.coerceAtMost(yMax + xNumberHH1)
				} else {
					ans = ans.coerceAtMost(yMax + xAboveMax)
				}
			}
		}
		xAboveMax = xAboveMax.coerceAtLeast(xs.last())
		countAbove += xs.size
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private inline operator fun <T> List<T>.component6(): T { return get(5) }

private val isOnlineJudge = System.getenv("ONLINE_JUDGE") == "true"
@Suppress("unused")
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
