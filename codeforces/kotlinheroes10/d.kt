package codeforces.kotlinheroes10

private fun solve(): Long {
	readln()
	val a = readInts()
	val series = mutableListOf<Int>()
	var ans = 0L
	for (i in a.indices) {
		if (a[i] == 0) continue
		series.add(a[i])
		if (i == a.lastIndex || a[i + 1] == 0) {
			val sum = series.sumOf { it.toLong() }
			ans += 2 * sum
			if (series.size % 2 != 0) {
				val best = (0 until series.size step 2).maxOf { series[it] }
				ans -= best
			}
			series.clear()
		}
	}
	return ans
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { println(solve()) }
