package facebook.y2020.qual

private fun solve(): Long {
	val (n, m) = readInts()
	val c = List(n) { readInt() }
	val a = mutableListOf(m to 0L)
	var low = 0
	val inf = c.fold(0L, Long::plus) + 1
	for (i in c.indices) {
		while (a[low].first < i) low++
		val cost = a[low].second
		if (i == n - 1) return cost.takeIf { it < inf } ?: -1L
		val costI = if (i == 0) 0L else c[i].toLong().takeIf { it > 0 } ?: inf
		val new = i + m to cost + costI
		while ((low < a.size) && (a.last().second >= new.second)) a.pop()
		a.add(new)
	}
	error("")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun <T> MutableList<T>.pop() = removeAt(lastIndex)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
