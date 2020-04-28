package codeforces.round637

private fun solve() {
	readLn()
	val a = readInts().map { it - 1 }
	println(if (good(a, 0)) "Yes" else "No")
}

fun good(a: List<Int>, x: Int): Boolean {
	if (x == a.size) return true
	val sz = a.size - x
	val f = a[x]
	for (v in f until sz) {
		if (a[x + v - f] != v) return false
	}
	return good(a, x + sz - f)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
