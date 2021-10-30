package codeforces.deltix2021summer

fun main() {
	val M = 1_000_000_007
	val MBigInteger = M.toBigInteger()
	readLn()
	val strengths = readInts()
	val n = strengths.size
	val probWin = List(n) { IntArray(n) }
	val probLose = List(n) { IntArray(n) }
	for (i in 0 until n) {
		for (j in 0 until n) {
			if (i == j) continue
			probWin[i][j] =	(strengths[i].toLong() * (strengths[i] + strengths[j]).toBigInteger().modInverse(MBigInteger).toLong() % M).toInt()
			probLose[j][i] = probWin[i][j]
		}
	}
	val masks = 1 shl n
	val pScc = IntArray(masks)
	var ans = 0L
	for (mask in 1 until masks) {
		if (mask.countOneBits() == 1) {
			pScc[mask] = 1
			continue
 		}
		var top = (mask - 1) and mask
		var rest = 1L
		while (top > 0) {
			var thisTop = pScc[top]
			for (i in 0 until n) {
				if (!top.hasBit(i)) continue
				for (j in 0 until n) {
					if (top.hasBit(j) || !mask.hasBit(j)) continue
					thisTop = (thisTop.toLong() * probWin[i][j] % M).toInt()
				}
			}
			if (mask == masks - 1) {
				ans += thisTop.toLong() * top.countOneBits()
			}
			rest = (rest + M - thisTop)
			top = (top - 1) and mask
		}
		pScc[mask] = (rest % M).toInt()
	}
	println((ans + pScc[masks - 1].toLong() * n) % M)
}

private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
