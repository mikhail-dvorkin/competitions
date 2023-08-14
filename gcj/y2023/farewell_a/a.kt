package gcj.y2023.farewell_a

private fun solve(): String {
	val d = readInts()
	val seen = mutableSetOf<String>()
	var collisions = false
	repeat(readInt()) {
		val s = readLn().map { d[it - 'A'] }.joinToString("")
		if (!seen.add(s)) collisions = true
	}
	return if (collisions) "YES" else "NO"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
