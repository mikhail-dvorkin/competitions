package codeforces.round901

private fun solve(): Double {
	val (n, m) = readInts()
	val d = List(n + 1) { DoubleArray(m - n + 1) }
	for (i in 1..n) {
		var kBest = 0
		for (s in d[i].indices) {
			val range = maxOf(kBest - 5, 0)..minOf(kBest + 11, s)
			var best = Double.MAX_VALUE
			for (k in range) {
				val new = (i + s - 1.0 - k) / (1 + k) + d[i - 1][s - k]
				if (new < best) {
					best = new
					kBest = k
				}
			}
			d[i][s] = best
		}
	}
	return d[n][m - n] * 2 + n
}

fun main() = repeat(1) { println(solve()) }

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
