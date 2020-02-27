package codeforces.kotlinheroes3

fun main() {
	val (m, kIn) = readInts()
	var mm = m
	listOf(2, 3, 5, 7).map { p ->
		var count = 0
		while (mm % p == 0) {
			mm /= p
			count++
		}
		count
	}
	if (mm != 1) return println(-1)
	mm = m
	var kk = kIn - 1L
	val memo = mutableMapOf<Pair<Int, Int>, Long>()
	for (len in 1..Int.MAX_VALUE) {
		val count = count(mm, len, memo)
		if (kk >= count) {
			kk -= count
			continue
		}
		val ans = MutableList(len) { 0 }
		for (i in 0 until len) {
			for (d in 1..9) {
				if (mm % d != 0) continue
				val here = count(mm / d, len - 1 - i, memo)
				if (kk >= here) {
					kk -= here
					continue
				}
				ans[i] = d
				mm /= d
				break
			}
		}
		return println(ans.joinToString(""))
	}
}

internal fun count(m: Int, len: Int, memo: MutableMap<Pair<Int, Int>, Long>): Long {
	val pair = m to len
	if (memo.containsKey(pair)) return memo[pair]!!
	if (len == 0) return if (m == 1) 1 else 0
	var res = 0L
	for (d in 1..9) {
		if (m % d != 0) continue
		res += count(m / d, len - 1, memo)
	}
	memo[pair] = res
	return res
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
