package codeforces.ozon2020

fun main() {
	val (n, m) = readInts()
	val max = n * (n - 1L) * (n - 2) / 6
	if (m > max) return println(-1)
	val ans = mutableListOf<Int>()
	var cur = 0
	for (i in 1..n) {
		val next = cur + (i - 1) / 2
		if (next > m) break
		cur = next
		ans.add(i)
	}
	if (ans.size == n && cur < m) return println(-1)
	if (ans.size < n) {
		val k = ans.size
		for (i in k + 1..2 * k) {
			val next = cur + (2 * k - i + 1) / 2
			if (next == m) {
				ans.add(i)
				break
			}
		}
		val inf = 1e5.toInt()
		var i = inf + 1
		while (ans.size < n) {
			ans.add(i)
			i += inf
		}
	}
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
