package codeforces.round901

val precalc = precalc(5000)

private fun precalc(n: Int): List<DoubleArray> {
	val p = List(n + 1) { DoubleArray(it) }
	p[1][0] = 1.0
	p[2][0] = 0.5
	for (i in 3..n) {
		p[i][0] = 1.0 / i
		for (j in 1 until i) {
			if (j >= 2) {
				p[i][j] = (j - 1.0) / i * p[i - 2][j - 2]
			}
			if (j < i - 1) {
				p[i][j] += (i - j - 1.0) / i * p[i - 2][j - 1]
			}
		}
	}
	return p
}

private fun solve() {
	val (n, m) = readIntArray()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (a, b) = readIntArray().map { it - 1 }
		nei[a].add(b)
	}
	val p = DoubleArray(n)

	p[n - 1] = 1.0
	for (i in n - 2 downTo 0) {
		val ps = nei[i].map { p[it] }.sortedDescending()
		var pWin = 0.0
		for (j in ps.indices) {
			pWin += ps[j] * precalc[ps.size][j]
		}
		p[i] = pWin
	}
	out.appendLine("" + p[0])
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
