package yandex.y2021.qual

fun main() {
	val tiles = List(readInt()) { toCanonical(readLn(), readLn()) }
		.groupBy { it }.mapValues { it.value.size }.toMutableMap()
	val (hei, wid) = readInts()
	val field = List(hei) { readLn() }
	for (i in 0 until hei step 2) for (j in 0 until wid step 2) {
		val needed = toCanonical(field[i].substring(j..j+1), field[i + 1].substring(j..j+1))
		val count = tiles[needed] ?: 0
		if (count == 0) return println("No")
		tiles[needed] = tiles[needed]!! - 1
	}
	println("Yes")
}

private fun toCanonical(top: String, bottom: String): String {
	val clockwise = top + bottom.reversed()
	return clockwise.indices.minOf { clockwise.drop(it) + clockwise.take(it) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
