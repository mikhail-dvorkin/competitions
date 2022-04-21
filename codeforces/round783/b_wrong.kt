package codeforces.round783

import kotlin.math.sign

fun solve(a: List<Int>): Int {
	val pref = mutableListOf(0L)
	val prefIndex = mutableListOf(0)
	val prefIndexRight = mutableListOf(0)
	var prefSum = 0L
	val dp = IntArray(a.size + 1)
	for (i in a.indices) {
		prefSum -= a[i]
		dp[i + 1] = dp[i] + a[i].sign
		if (prefSum > pref.last()) {
			pref.add(prefSum)
			prefIndex.add(i + 1)
			prefIndexRight.add(i + 1)
			continue
		}
		val bs = pref.binarySearch(prefSum)
		if (bs >= 0) {
			val j = prefIndexRight[bs]
			dp[i + 1] = maxOf(dp[i + 1], dp[j])
			prefIndexRight[bs] = i + 1
		}
		if (prefSum < pref.last()) {
			val b = if (bs >= 0) bs + 1 else - 1 - bs
			val j = prefIndex[b]
			dp[i + 1] = maxOf(dp[i + 1], dp[j] + i + 1 - j)
		}
		if (pref.size >= 2 && prefSum > pref[pref.size - 2] && prefSum < pref.last() && dp[i + 1] - dp[prefIndex.last()] > i + 1 - prefIndex.last()) {
			pref.removeLast()
			prefIndex.removeLast()
			prefIndexRight.removeLast()
			pref.add(prefSum)
			prefIndex.add(i + 1)
			prefIndexRight.add(i + 1)
		}

		var improve = false
		while (true) {
			if (pref.isNotEmpty() && prefSum <= pref.last() && dp[i + 1] - dp[prefIndex.last()] > i + 1 - prefIndex.last()) {
				pref.removeLast()
				prefIndex.removeLast()
				prefIndexRight.removeLast()
				improve = true
				continue
			}
			break
		}
		if (improve) {
			pref.add(prefSum)
			prefIndex.add(i + 1)
			prefIndexRight.add(i + 1)
		}
	}
	return dp.last()
}

private fun solve() {
	readLn()
	val a = readInts()
	println(solve(a))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
