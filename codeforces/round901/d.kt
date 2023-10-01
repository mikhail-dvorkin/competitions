package codeforces.round901

private fun solve(): Double {
	val (n, m) = readInts()
	val d = List(n + 1) { DoubleArray(m - n + 1) }
	for (i in 1..n) {
		var kPrevBest = 0
		for (s in d[i].indices) {
			val range = maxOf(kPrevBest - 56, 0)..minOf(kPrevBest + 1, s)
			val (kBest, best) = range.minByAndValue { k -> (i + s - 1.0 - k) / (1 + k) + d[i - 1][s - k] }
			d[i][s] = best
			kPrevBest = kBest
		}
	}
	return d[n][m - n] * 2 + n
}

private inline fun <T, R : Comparable<R>> Iterable<T>.minByAndValue(crossinline selector: (T) -> R) = asSequence().map { it to selector(it) }.minBy { it.second }

fun main() = repeat(1) { println(solve()) }

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
