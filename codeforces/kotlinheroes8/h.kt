package codeforces.kotlinheroes8

fun main() {
	val M = 998244353
	val DEN = 1_000_000
	val DEN_INV = DEN.toBigInteger().modInverse(M.toBigInteger()).toInt()
	val (n, wid, hei) = readInts()
	val pSlash = IntArray(wid + hei + 1) { 1 } // prob of being free
	val pBackslash = IntArray(wid + hei + 1) { 1 }
	val vertical = BooleanArray(wid + 1)
	val horizontal = BooleanArray(hei + 1)
	vertical[0] = true
	vertical[wid] = true
	horizontal[0] = true
	horizontal[hei] = true
	repeat(n) {
		val (x, y, prob) = readInts()
		val pMod = ((DEN - prob).toLong() * DEN_INV % M).toInt()
		vertical[x] = true
		horizontal[y] = true
		pBackslash[x + y] = (pBackslash[x + y].toLong() * pMod % M).toInt()
		pSlash[x - y + hei] = (pSlash[x - y + hei].toLong() * pMod % M).toInt()
	}
	var edgesExpectation = vertical.count { it } * hei + horizontal.count { it } * wid
	var isolatedExpectation = 0
	for (x in 0..wid) for (y in 0..hei) {
		if (!vertical[x] && !horizontal[y]) {
			val pIsolated = (pBackslash[x + y].toLong() * pSlash[x - y + hei] % M).toInt()
			isolatedExpectation = (isolatedExpectation + pIsolated) % M
		}
		if (x < wid && y < hei) {
			val pIsolated = (pBackslash[x + y + 1].toLong() * pSlash[x - y + hei] % M).toInt()
			isolatedExpectation = (isolatedExpectation + pIsolated) % M
		}
		if (y - 1 >= 0 && x + 1 <= wid) {
			val pEdge = (M + 1 - pBackslash[x + y]) % M
			edgesExpectation = ((edgesExpectation + 2L * pEdge) % M).toInt()
		}
		if (y + 1 <= hei && x + 1 <= wid) {
			val pEdge = (M + 1 - pSlash[x - y + hei]) % M
			edgesExpectation = ((edgesExpectation + 2L * pEdge) % M).toInt()
		}
	}
	val vertices = (wid + 1) * (hei + 1) + wid * hei
//	println(vertices)
//	println(edgesExpectation)
//	println(isolatedExpectation)
	val ans = (1L + isolatedExpectation + edgesExpectation - vertices + M) % M
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
