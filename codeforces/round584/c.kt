package codeforces.round584

private fun solve() {
	readLn()
	val s = readLn()
	for (c in '0'..'9') {
		val last = s.indexOfLast { it < c }
		val first = s.indices.map { s[it] < c || (s[it] == c && it > last) }
		var t = s.filterIndexed { index, c -> first[index] } + s.filterIndexed { index, c -> !first[index] }
		if (t.zipWithNext().all { it.first <= it.second }) {
			println(first.map { if (it) '1' else '2' }.joinToString(""))
			return
		}
	}
	println("-")
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
