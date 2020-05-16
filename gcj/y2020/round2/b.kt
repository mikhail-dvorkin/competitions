package gcj.y2020.round2

private fun solve(): String {
	val (n, m) = readInts()
	val data = (listOf(0) + readInts()).toIntArray()
	val edges = List(m) { readInts().map { it - 1 } }
	val added = IntArray(n)
	data.withIndex().filter { it.value < 0 }.forEach { entry ->
		added[(-entry.value until n).first { added[it] == 0 }] = entry.index
	}
	val byTime = data.indices.minus(added.toSet()).sortedBy { data[it] }.toMutableList()
	for (i in 1 until n) if (added[i] == 0) added[i] = byTime.removeAt(0)
	var x = 1
	while (x < n) {
		if (data[added[x]] > 0) { x++; continue }
		val y = (x + 1 until n).firstOrNull { data[added[it]] != data[added[x]] } ?: n
		for (i in x until y) data[added[i]] = data[added[x - 1]] + 1
		x = y
	}
	return edges.joinToString(" ") { (u, v) -> (data[u] - data[v]).abs().coerceAtLeast(1).toString() }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
