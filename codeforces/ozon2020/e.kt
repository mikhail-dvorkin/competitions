package codeforces.ozon2020

fun main() {
	val (n, m) = readInts()
	val ans = mutableListOf<Int>()
	var cur = 0
	for (i in 1..n) {
		val next = cur + (i - 1) / 2
		if (next > m) break
		cur = next
		ans.add(i)
	}
	if (ans.size == n && cur < m) return println(-1)
	if (ans.size < n) ans.add(2 * (ans.size + cur - m))
	while (ans.size < n) ans.add(2 * n * ans.size + 1)
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
