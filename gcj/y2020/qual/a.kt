package gcj.y2020.qual

private fun solve(): String {
	val a = List(readInt()) { readInts() }
	val trace = a.indices.sumBy { a[it][it] }
	val aTransposed = a[0].indices.map { i -> a.map { it[i] } }
	val (rows, cols) = listOf(a, aTransposed).map { it.count { row -> row.toSet().size < row.size } }
	return listOf(trace, rows, cols).joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
