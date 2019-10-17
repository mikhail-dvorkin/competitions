package codeforces.round584

private fun solve(): String {
	readLn()
	val s = readLn()
	for (c in '0'..'9') {
		val lastC = s.indexOfLast { it < c }
		val first = s.indices.map { s[it] < c || s[it] == c && it > lastC }
		val t = s.toList().zip(first).sortedBy { !it.second }.map { it.first }
		if (t.zipWithNext { a, b -> a <= b }.all { it }) return first.joinToString("") { if (it) "1" else "2" }
	}
	return "-"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
