package gcj.y2022.qual

private fun solve(printers: Int = 3, need: Int = 1_000_000): String {
	val a = List(printers) { readInts() }
	val min = List(a[0].size) { i -> a.minOf { it[i] } }
	if (min.sum() < need) return "IMPOSSIBLE"
	var toTake = need
	return min.map { x -> minOf(x, toTake).also { toTake -= it } }.joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
