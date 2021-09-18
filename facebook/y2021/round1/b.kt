package facebook.y2021.round1

private fun solve(): String {
	// a is \, b is /
	val (n, m, a, b) = readInts()
	val allOnes = n + m - 1
	if (minOf(a, b) < allOnes) return "Impossible"
	val field = List(n) { IntArray(m) { 1 } }
	field[0][0] += a - allOnes
	field[0][m - 1] += b - allOnes
	return "Possible\n" + field.joinToString("\n") { it.joinToString(" ") }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
