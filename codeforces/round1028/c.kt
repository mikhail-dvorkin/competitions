package codeforces.round1028

private fun solve() {
	val (n, m, p100) = readInts()
	val h = readInts().sorted().map { it - 1 }
	val p = p100 / 100.0
	val q = 1 - p

	val dpEven = List(h[0] + 1) { List(n) { DoubleArray(m + 1) } }
	for (k in 0..m) {
		dpEven[0][0][k] = 1.0
	}
	for (i in 0..h[0]) {
		val ip = maxOf(i - 1, 0)
		if (i > 0) for (k in 1..m) {
			dpEven[i][0][k] = p * dpEven[ip][0][k - 1] + q * maxOf(dpEven[i][0][k - 1], dpEven[i - 1][n - 1][k - 1])
		}
		for (j in 1 until n) {
			val d1 = dpEven[i][j]
			val d2 = dpEven[ip][j]
			val d3 = dpEven[i][j - 1]
			for (k in 1..m) {
				d1[k] = p * d2[k - 1] + q * d3[k - 1]
			}
		}
	}

	val extras = h.sumOf { it - h[0] }
	if (extras > m) {
		println(0.0)
		return
	}

	val dp = List(m + 1) { DoubleArray(extras + 1) }
	for (k in 0..m) {
		val before = m - k
		val beforeNoShine = extras
		val beforeShine = before - beforeNoShine
		if (beforeShine < 0) continue
		val x = maxOf(h[0] - beforeShine, 0)
		dp[k][0] = dpEven[x][0][k]
	}
	for (k in 1..m) {
		val dpk = dp[k]
		val dpk1 = dp[k - 1]
		for (i in 1..minOf(k, extras)) {
			dpk[i] = p * dpk1[i] + q * dpk1[i - 1]
		}
	}
	println(dp[m][extras])
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
