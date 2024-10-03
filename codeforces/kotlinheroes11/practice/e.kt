package codeforces.kotlinheroes11.practice

private fun solve() {
	val s = readln()
	var m = readLong() - 1
	val start = StringBuilder()
	var i = 0
	for (length in s.length downTo 1) {
		if (m < length) return print((start.toString() + s.drop(i))[m.toInt()])
		m -= length
		while (i < s.length && (start.isEmpty() || start.last() <= s[i])) {
			start.append(s[i++])
		}
		start.setLength(start.length - 1)
	}
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readLong() = readln().toLong()
