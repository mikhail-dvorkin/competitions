package codeforces.globalround20

private fun solve() {
	val n = readInt()
	val a = readInts()
	val count = IntArray(n + 1)
	val pos = List(n + 1) { mutableListOf<Int>() }
	for (i in a.indices) {
		count[a[i]]++
		pos[a[i]].add(i)
	}
	val often = count.indices.sortedBy { count[it] }
	val mostOften = often.last()
	val ans = IntArray(n) { -1 }
	for (i in 0..often.size - 2) {
		val v = often[i]
		val u = often[i + 1]
		for (j in 0 until count[v]) {
			ans[pos[u][j]] = v
		}
	}
	for (i in ans.indices) {
		if (ans[i] == -1) ans[i] = mostOften
	}
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
