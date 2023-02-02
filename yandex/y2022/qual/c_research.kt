package yandex.y2022.qual

private fun research() {
	val magic = 1 shl 17
	val dp = IntArray(magic + 1) { magic }
	dp[0] = 0
	for (k in 0..magic) {
		val term = k * (k + 1) / 2
		if (term > magic) break
		for (i in 0..magic - term) dp[i + term] = minOf(dp[i + term], dp[i] + 1)
	}
	println(dp.sorted())
}
