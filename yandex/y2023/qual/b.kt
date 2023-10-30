package yandex.y2023.qual

private fun solve() {
	val (n, m) = readInts()
	val a = readInts()
	val aSorted = a.sorted()
	val y = aSorted[m - 1]
	val yTake = aSorted.take(m).count { it == y }
	val xDefinitely = a.indices.filter { a[it] < y }
	val xY = a.indices.filter { a[it] == y }
	val xLeft = xDefinitely.firstOrNull() ?: n
	val xRight = xDefinitely.lastOrNull() ?: -1
	val ans = (0..(xY.size - yTake)).minOf { i ->
		val xYLeft = xY[i]
		val xYRight = xY[i + yTake - 1]
		maxOf(xYRight, xRight) - minOf(xYLeft, xLeft) + 1
	}
	println(ans)
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(1) { solve() }
