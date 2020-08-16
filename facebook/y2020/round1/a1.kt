package facebook.y2020.round1

private fun solve(M: Int = 1_000_000_007): Int {
	val (n, k, w) = readInts()
	val (L, h) = List(2) { readInts().toMutableList().also { list ->
		val (a, b, c, d) = readInts()
		for (i in k until n) {
			list.add(((a.toLong() * list[i - 2] + b.toLong() * list[i - 1] + c) % d + 1).toInt())
		}
	}}
	var ans = 1
	var perimeter = 0L
	val height = mutableMapOf<Int, Int>()
	fun perimeter(xMax: Int): Long {
		var res = 0L
		for (x in xMax - w - 1 .. xMax) {
			val y = height.getOrDefault(x, 0)
			val yLeft = height.getOrDefault(x - 1, 0)
			if (y > 0) res += 2
			res += (y - yLeft).abs()
		}
		return res
	}
	for (i in h.indices) {
		val xMax = L[i] + w
		perimeter -= perimeter(xMax)
		for (x in L[i] until xMax) {
			height[x] = maxOf(height.getOrDefault(x, 0), h[i])
		}
		perimeter += perimeter(xMax)
		ans = ((ans.toLong() * (perimeter % M)) % M).toInt()
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
