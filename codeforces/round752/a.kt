package codeforces.round752

private fun solve(): String {
	readLn()
	val a = readInts().toMutableList()
	while (a.isNotEmpty()) {
		var improved = false
		for (i in a.indices.reversed()) {
			if (a[i] % (i + 2) != 0) {
				a.removeAt(i)
				improved = true
				break
			}
		}
		if (!improved) break
	}
	return if (a.isEmpty()) "YES" else "NO"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
