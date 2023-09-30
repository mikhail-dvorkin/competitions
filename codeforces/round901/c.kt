package codeforces.round901

private fun precalc(n: Int = 5000): List<DoubleArray> {
	val p = List(n + 1) { DoubleArray(it) }
	for (i in 1..n) {
		p[i][0] = 1.0 / i
		for (j in 1 until i) {
			if (j >= 2) p[i][j] = (j - 1.0) / i * p[i - 2][j - 2]
			if (j < i - 1) p[i][j] += (i - j - 1.0) / i * p[i - 2][j - 1]
		}
	}
	return p
}

private val precalc = precalc()

private fun solve(): Double {
	val (n, m) = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
	}

	val p = DoubleArray(n)
	p[n - 1] = 1.0
	for (i in n - 2 downTo 0) {
		val ps = nei[i].map { p[it] }.sortedDescending()
		p[i] = ps.indices.sumOf { j -> ps[j] * precalc[ps.size][j] }
	}
	return p[0]
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
