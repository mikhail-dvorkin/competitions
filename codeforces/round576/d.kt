package codeforces.round576

fun main() {
	val n = readInt()
	val f = Array(n) { readLn().map { if (it == '#') 1 else 0 } }
	val cum = Array(n + 1) { IntArray(n + 1) }
	for (i in 0 until n) {
		for (j in 0 until n) {
			cum[i + 1][j + 1] = cum[i][j + 1] + cum[i + 1][j] - cum[i][j] + f[i][j]
		}
	}
	val dp = Array(n + 1) { Array(n + 1) { Array(n + 1) { IntArray(n + 1) }}}
	for (i1 in n - 1 downTo 0) {
		for (i2 in i1 + 1..n) {
			for (j1 in n - 1 downTo 0) {
				for (j2 in j1 + 1..n) {
					if (sum(cum, i1, j1, i2, j2) == 0) continue
					var res = maxOf(i2 - i1, j2 - j1)
					for (i in i1 until i2) {
						if (sum(cum, i, j1, i + 1, j2) != 0) continue
						res = minOf(res, dp[i1][j1][i][j2] + dp[i + 1][j1][i2][j2])
					}
					for (j in j1 until j2) {
						if (sum(cum, i1, j, i2, j + 1) != 0) continue
						res = minOf(res, dp[i1][j1][i2][j] + dp[i1][j + 1][i2][j2])
					}
					dp[i1][j1][i2][j2] = res
				}
			}
		}
	}
	println(dp[0][0][n][n])
}

private fun sum(cum: Array<IntArray>, i1: Int, j1: Int, i2: Int, j2: Int) =
		cum[i2][j2] - cum[i1][j2] - cum[i2][j1] + cum[i1][j1]

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
