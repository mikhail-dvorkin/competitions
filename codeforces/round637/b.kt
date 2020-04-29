package codeforces.round637

private val digits = listOf("1110111", "0010010", "1011101", "1011011", "0111010",
		"1101011", "1101111", "1010010", "1111111", "1111011").map { it.toInt(2) }

fun main() {
	val (n, k) = readInts()
	val a = List(n) {
		val shown = readLn().toInt(2)
		digits.mapIndexedNotNull { d, digit ->
			(d to Integer.bitCount(digit xor shown)).takeIf { digit.inv() and shown == 0 }
		}
	}
	val can = List(n + 1) { BooleanArray(k + 1) }
	can[n][0] = true
	for (i in a.indices.reversed()) {
		for ((_, need) in a[i]) {
			for (j in 0..k - need) if (can[i + 1][j]) can[i][j + need] = true
		}
	}
	var x = k
	println(if (!can[0][x]) -1 else a.indices.joinToString("") { i ->
		val (d, need) = a[i].last { can[i + 1].getOrFalse(x - it.second) }
		x -= need
		d.toString()
	})
}

private fun BooleanArray.getOrFalse(index: Int) = getOrNull(index) ?: false
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
