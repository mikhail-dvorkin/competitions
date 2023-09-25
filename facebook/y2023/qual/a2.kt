package facebook.y2023.qual

private fun solve(): Long {
	val (a, b, c) = readLongs()
	return listOf(0, 1, 2, c / a).maxOf { s ->
		if (s * a > c) return@maxOf 0
		val d = (c - s * a) / b
		minOf(s + 2 * d, 2 * (s + d) - 1)
	}
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
