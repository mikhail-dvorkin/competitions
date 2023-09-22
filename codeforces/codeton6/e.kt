package codeforces.codeton6

import java.util.BitSet

private fun solve(): Int {
	readln()
	val a = readInts()
	val highMex = (a.size + 1).takeHighestOneBit() * 2
	val mark = IntArray(a.size + 1) { -1 }
	val memo = List(highMex) { BitSet() }
	val dp = mutableListOf(BitSet())
	val (dpMax, dpSum) = List(2) { IntArray(a.size + 1) { 0 } }
	dp[0][0] = true
	dpSum[0] = 1
	var leastAbsent = 1
	for (i in a.indices) {
		val dpCurrent = dp[i].clone() as BitSet
		var mex = 0
		for (j in i downTo 0) {
			mark[a[j]] = i
			if (a[j] == mex) {
				while (mark[mex] == i) mex++
				val t = (dpMax[j] or mex).takeHighestOneBit() * 2 - 1
				if (leastAbsent > t) continue
				val memoJ = memo[dpSum[j]]
				if (memoJ[mex]) continue
				memoJ[mex] = true
				val dpJ = dp[j]
				for (k in 0..dpMax[j]) if (dpJ[k]) dpCurrent[k xor mex] = true
				while (dpCurrent[leastAbsent]) leastAbsent++
			}
		}
		dp.add(dpCurrent)
		dpSum[i + 1] = dpCurrent.cardinality()
		dpMax[i + 1] = dpCurrent.length() - 1
	}
	return dpMax.last()
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
