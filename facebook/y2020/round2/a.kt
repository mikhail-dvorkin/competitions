package facebook.y2020.round2

private fun solve(): Long {
	val (n, k) = readInts()
	val (s, x, y) = List(3) {
		val array = readInts().toIntArray() + IntArray(n - k) { 0 }
		val (a, b, c, d) = readInts()
		for (i in k until n) {
			array[i] = ((a.toLong() * array[i - 2] + b.toLong() * array[i - 1] + c) % d).toInt()
		}
		array
	}
	var up = 0L
	var down = 0L
	var canUp = 0L
	var canDown = 0L
	for (i in s.indices) {
		if (s[i] < x[i]) up += x[i] - s[i]
		canUp += maxOf(0, x[i] + y[i] - s[i])
		if (s[i] > x[i] + y[i]) down += s[i] - x[i] - y[i]
		canDown += maxOf(0, s[i] - x[i])
	}
	if (up > canDown || down > canUp) return -1L
	return maxOf(up, down)
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
