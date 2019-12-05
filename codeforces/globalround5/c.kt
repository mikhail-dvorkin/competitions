package codeforces.globalround5

private fun solve(a: List<Pair<Int, List<Int>>>): Int? {
	if (a.size == 1) return a.first().first
	val c = a.groupBy { it.second[0] }.toSortedMap().values.mapNotNull { points ->
		solve(points.map { it.first to it.second.drop(1) })
	}
	for (i in 0 until c.size / 2) println("${c[2 * i] + 1} ${c[2 * i + 1] + 1}")
	return if (c.size % 2 == 1) c.last() else null
}

fun main() {
	solve(List(readInt()) { it to readInts() })
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
