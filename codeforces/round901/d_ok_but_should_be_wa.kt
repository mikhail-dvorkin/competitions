package codeforces.round901

private fun solve() {
	val (n, m) = readInts()
	val d = List(n + 1) { DoubleArray(m - n + 1) }
	for (i in 1..n) {
		var kBest = 0
		for (s in d[i].indices) {
			d[i][s] = Double.MAX_VALUE
			val range = maxOf(kBest - 5, 0)..minOf(kBest + 11, s)
			for (k in range) {
				val new = (i + s * 1.0 - 1 - k) / (1 + k) + d[i - 1][s - k]
				if (new < d[i][s]) {
					d[i][s] = new
					kBest = k
				}
			}
		}
	}
	out.appendLine("" + (d[n][m - n] * 2 + n))
}

fun main() = repeat(1) { solve() }.also { out.close() }

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
