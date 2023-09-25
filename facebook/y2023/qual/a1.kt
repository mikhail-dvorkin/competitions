package facebook.y2023.qual

private fun solve(): Boolean {
	val (s, d, k) = readInts()
	return 2 * (s + d) >= k + 1 && s + 2 * d >= k
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve().iif("YES", "NO")}") }
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
