package codeforces.round637

val digits = listOf("1110111", "0010010", "1011101", "1011011", "0111010", "1101011", "1101111", "1010010", "1111111", "1111011").map { it.toInt(2) }

fun main() {
	val (n, k) = readLine()!!.split(" ").map { it.toInt() }
	val a = List(n) { readLine()!!.toInt(2) }
	val dp = List(n + 1) { BooleanArray(k + 1) }
	dp[n][0] = true
	for (i in a.indices.reversed()) {
		for (d in digits.indices) if ((digits[d] and a[i]) == a[i]) {
			val on = Integer.bitCount(digits[d] xor a[i])
			for (j in 0..k - on) if (dp[i + 1][j]) dp[i][j + on] = true
		}
	}
	var x = k
	if (!dp[0][x]) return println(-1)
	val ans = StringBuilder()
	for (i in a.indices) {
		for (j in 0..k) if (dp[i + 1][j]) dp[i][j] = true
		for (d in digits.indices.reversed()) if ((digits[d] and a[i]) == a[i]) {
			val on = Integer.bitCount(digits[d] xor a[i])
			if (x - on >= 0 && dp[i + 1][x - on]) {
				ans.append(d)
				x -= on
				break
			}
		}
	}
	println(ans)
}
