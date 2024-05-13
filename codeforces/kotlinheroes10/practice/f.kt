package codeforces.kotlinheroes10.practice

private fun solve() {
	readln()
	val a = readInts()
	val nei = List(a.size) { mutableListOf<Int>() }
	for (i in a.indices) {
		val j = i - a[i]
		if (j >= 0) nei[j].add(i + 1)
		val k = i + 1 + a[i]
		if (k <= a.size) nei[i].add(k)
	}
	val mark = BooleanArray(a.size + 1)
	mark[0] = true
	for (i in a.indices) {
		if (!mark[i]) continue
		for (j in nei[i]) mark[j] = true
	}
	println(mark.last().iif("YES", "NO"))
}

private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { solve() }
