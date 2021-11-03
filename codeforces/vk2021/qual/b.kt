package codeforces.vk2021.qual

fun main() {
	val bySide = mutableMapOf<Int, Int>()
	val byType = mutableMapOf<Long, Int>()
	repeat(readInt()) {
		val (x, y) = readInts()
		bySide[x] = bySide.getOrDefault(x, 0) + 1
		if (y != x) {
			bySide[y] = bySide.getOrDefault(y, 0) + 1
			val id = minOf(x, y) * 1_000_000_000L + maxOf(x, y)
			byType[id] = byType.getOrDefault(id, 0) + 1
		}
	}
	var ans = 0L
	for (x in bySide.keys) {
		val count = bySide[x]!!
		ans += count * (count - 1L) / 2
	}
	for (id in byType.keys) {
		val count = byType[id]!!
		ans -= count * (count - 1L) / 2
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
