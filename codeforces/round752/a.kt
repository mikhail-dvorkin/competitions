package codeforces.round752

private fun solve(): String {
	readLn()
	val a = readInts().toMutableList()
	while (a.isNotEmpty()) {
		val index = a.indices.reversed().firstOrNull { a[it] % (it + 2) != 0 }
			?: return "NO"
		a.removeAt(index)
	}
	return "YES"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
