package codeforces.kotlinheroes4

fun main() = repeat(readInt()) {
	val (n, k, x, y) = readInts()
	val a = readLongs().sorted()
	var ans = 0L
	var sum = a.sum()
	var amount = a.size
	while (k * n.toLong() < sum) {
		ans += x
		sum -= a[amount - 1]
		amount--
	}
	if (amount > 0 && a[amount - 1] > k) ans += y
	println(minOf(ans, a.count { it > k } * x.toLong()))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
