package codeforces.round773

private fun solve() {
	readInt()
	val a = readInts().toIntArray()
	val groups = a.groupBy { it }
	if (groups.values.any { it.size % 2 != 0 }) return println(-1)
	var start = 0
	val insertions = mutableListOf<Pair<Int, Int>>()
	val tandems = mutableListOf<Int>()
	var x = 0
	while (start < a.size) {
		val repeat = a.drop(start + 1).indexOf(a[start]) + start + 1
		val rev = mutableListOf<Int>()
		for (i in start + 1 until repeat) {
			insertions.add(x + (repeat - start) + (i - start) to a[i])
			rev.add(a[i])
		}
		tandems.add((repeat - start) * 2)
		x += (repeat - start) * 2
		rev.reverse()
		for (i in rev.indices) {
			a[start + 2 + i] = rev[i]
		}
		start += 2
	}
	println(insertions.size)
	for (insertion in insertions) {
		println("${insertion.first} ${insertion.second}")
	}
	println(tandems.size)
	println(tandems.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
