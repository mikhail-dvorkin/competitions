package codeforces.ozon2020

fun main() {
	val (n, m) = readInts()
	val a = readInts().sorted()
	if (n > m) return println(0)
	var ans = 1L
	for (i in a.indices) {
		for (j in 0 until i) {
			ans = ans * (a[i] - a[j]) % m
		}
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
