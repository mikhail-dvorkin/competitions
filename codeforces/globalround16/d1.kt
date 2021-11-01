package codeforces.globalround16

private fun solve() {
	val (hei, wid) = readInts()
	val a = readInts()
	var ans = 0L
	for (i in a.indices) {
		for (j in 0 until i) {
			if (a[j] < a[i]) ans++
		}
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
