package codeforces.vk2021.qual

fun main() {
	val n = readInt()
	var bestSize = n + 1
	var ans = listOf<CharArray>()
	for (d in 1..n) {
		val s = d * d
		val other = n - s
		if (other < 0 || other % 2 != 0) continue
		val size = d + ((other / 2) + d - 1) / d
		if (size < bestSize) {
			bestSize = size
			ans = List(size) { CharArray(size) { '.' } }
			for (i in 0 until d) for (j in 0 until d) ans[i][j] = 'o'
			for (i in 0 until other / 2) {
				ans[d + i / d][i % d] = 'o'
				ans[i % d][d + i / d] = 'o'
			}
		}
	}
	if (ans.isEmpty()) return println(-1)
	println(ans.size)
	for (s in ans.reversed()) println(s.concatToString())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
