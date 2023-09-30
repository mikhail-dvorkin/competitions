package codeforces.round901

private fun solve(): Long {
	val (_, _, k) = readInts()
	val (a, b) = List(2) { readInts().toMutableList() }
	val moves = mutableListOf<Long>()
	var i = 0
	while (i < k) {
		val move = if (i % 2 == 0) makeMove(a, b) else makeMove(b, a)
		moves.add(move)
		if (i >= 4 && moves[moves.size - 1] == moves[moves.size - 3] && moves[moves.size - 2] == moves[moves.size - 4]) {
			i += maxOf((k - 1 - i) / 2 * 2, 0)
		}
		i++
	}
	return a.sumOf { it.toLong() }
}

private fun makeMove(a: MutableList<Int>, b: MutableList<Int>): Long {
	val aMin = a.min()
	val bMax = b.max()
	if (aMin >= bMax) return 0
	a.remove(aMin); a.add(bMax)
	b.add(aMin); b.remove(bMax)
	return aMin with bMax
}

private infix fun Int.with(that: Int) = (toLong() shl 32) or that.toLong()

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
