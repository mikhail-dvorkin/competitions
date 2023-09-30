package codeforces.round901

private fun solve() {
	val (n, m, k) = readIntArray()
	val a = readIntArray().toMutableList()
	val b = readIntArray().toMutableList()
	var i = 0
	val moves = mutableListOf<Long>()
	while (i < k) {
		val move: Long
		if (i % 2 == 0) {
			val aMin = a.min()
			val bMax = b.max()
			if (aMin < bMax) {
				a.remove(aMin); a.add(bMax)
				b.add(aMin); b.remove(bMax)
				move = aMin * 1_000_000_000L + bMax
			} else move = 0
		} else {
			val bMin = b.min()
			val aMax = a.max()
			if (bMin < aMax) {
				b.remove(bMin); b.add(aMax)
				a.add(bMin); a.remove(aMax)
				move = bMin * 1_000_000_000L + aMax
			} else move = 0
		}
		moves.add(move)
		if (i >= 4 && moves[moves.size - 1] == moves[moves.size - 3] && moves[moves.size - 2] == moves[moves.size - 4]) {
			i += maxOf((k - 5 - i) / 2 * 2, 0)
		}
		i++
	}
	out.appendLine("" + a.sumOf { it.toLong() })
}

fun main() = repeat(readInt()) { solve() }.also { out.close() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readIntArray() = readln().parseIntArray()
private fun readLongs() = readStrings().map { it.toLong() }

private fun String.parseIntArray(): IntArray {
	val result = IntArray(count { it == ' ' } + 1)
	var i = 0; var value = 0
	for (c in this) {
		if (c != ' ') {
			value = value * 10 + c.code - '0'.code
			continue
		}
		result[i++] = value
		value = 0
	}
	result[i] = value
	return result
}

private val `in` = System.`in`.bufferedReader()
private val out = System.out.bufferedWriter()
private fun readln() = `in`.readLine()
