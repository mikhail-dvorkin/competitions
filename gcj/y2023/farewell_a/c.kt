package gcj.y2023.farewell_a

private fun solve(): String {
	readLn()
	val a = readInts()
	val aFirst = a.withIndex().filter { it.index == 0 || it.value != a[it.index - 1] }.map { it.value }
	if (aFirst.size != aFirst.toSet().size) return "IMPOSSIBLE"
	return aFirst.joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
