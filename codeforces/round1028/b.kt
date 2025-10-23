package codeforces.round1028

private fun solve(): String {
	val (_, queriesCount) = readInts()
	val b = readInts().toIntArray()
	val a = b.clone()
	val queries = List(queriesCount) { readInts().map { it - 1 } }
	for ((x, y, z) in queries.reversed()) {
		val res = a[z]
		if (z != x && z != y) a[z] = 1
		a[x] = maxOf(a[x], res)
		a[y] = maxOf(a[y], res)
	}
	val ans = a.clone()
	for ((x, y, z) in queries) {
		a[z] = minOf(a[x], a[y])
	}
	return if (a.contentEquals(b)) ans.joinToString(" ") else "-1"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
