package facebook.y2020.round1

private fun solve(): Int {
	val (n, m, k, _) = readInts()
	val (p, q) = listOf(n, m).map { size -> readInts().toMutableList().also { list ->
		val (a, b, c, d) = readInts()
		for (i in k until size) {
			list.add(((a.toLong() * list[i - 2] + b.toLong() * list[i - 1] + c) % d + 1).toInt())
		}
	}.sorted() }
	var (low, high) = 0 to (p[0] - q[0]).abs() + q.last() - q[0]
	while (low + 1 < high) {
		val time = (low + high) / 2
		var i = 0
		for (x in p) {
			val extraTime = (time - (x - q[i]).abs()).takeIf { it >= 0 } ?: continue
			val y = maxOf(q[i] + extraTime, x + extraTime / 2)
			while (i < m && q[i] <= y) i++
			if (i == m) break
		}
		if (i == m) high = time else low = time
	}
	return high
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
