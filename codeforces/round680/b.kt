package codeforces.round680

const val M = 998244353

fun main() {
	val n = readInt()
	val a = readInts().sorted()
	var k = (- a.take(n).sumOf { it.toLong() } + a.drop(n).sumOf { it.toLong() }) % M
	for (i in 1..n) {
		k = (k * (n + i)) % M
		k = (k * i.toBigInteger().modInverse(M.toBigInteger()).toLong()) % M
	}
	println(k)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
