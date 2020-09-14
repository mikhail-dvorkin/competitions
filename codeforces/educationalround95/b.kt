package codeforces.educationalround95

private fun solve() {
	val n = readInt()
	val a = readInts().toMutableList()
	val b = readInts()
	val aMovable = a.withIndex().filter { b[it.index] == 0 }.map { it.value }.sortedDescending()
	var j = 0
	for (i in a.indices) {
		if (b[i] == 0) {
			a[i] = aMovable[j++]
		}
	}
	println(a.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
