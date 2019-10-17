package codeforces.globalround5

fun main() {
	val n = readInt()
	val a = List(n) { it to readInts() }
	solve(a)
}

private fun solve(a: List<Pair<Int, List<Int>>>): Int {
	val b = a.groupBy { it.second[0] }
	val c = mutableListOf<Int>()
	for (x in b.keys.sorted()) {
		val points = b[x] ?: error("")
		if (points.size == 1) {
			c.add(points.first().first)
			continue
		}
		val cutPoints = points.map { it.first to it.second.drop(1) }
		val left = solve(cutPoints)
		if (left >= 0) {
			c.add(left)
		}
	}
	while (c.size >= 2) {
		println(c.takeLast(2).map{ it + 1 }.joinToString(" "))
		repeat(2) { c.removeAt(c.lastIndex) }
	}
	return if (c.isEmpty()) -1 else c.first()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
