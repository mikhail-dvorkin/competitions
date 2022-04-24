package codeforces.globalround20

private fun solve(): Int {
	readLn()
	val a = readInts()
	val equals = (0..a.size - 2).filter { a[it] == a[it + 1] }
	if (equals.isEmpty()) return 0
	val len = equals.maxOrNull()!! - equals.minOrNull()!!
	if (len <= 1) return len
	return len - 1
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
