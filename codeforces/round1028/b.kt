package codeforces.round1028

private fun solve() {
	val (_, queriesCount) = readInts()
	val b = readInts().toIntArray()
	val a = b.clone()
	val queries = List(queriesCount) { readInts().map { it - 1 } }
	for ((x, y, z) in queries.reversed()) {
		val res = a[z]
		if (z != x && z != y) {
			a[z] = 0
		}
		a[x] = maxOf(a[x], res)
		a[y] = maxOf(a[y], res)
	}
	val ans = a.clone()
	for ((x, y, z) in queries) {
		a[z] = minOf(a[x], a[y])
	}
	if (!a.contentEquals(b)) {
		println(-1)
		return
	}
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
