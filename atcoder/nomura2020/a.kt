package atcoder.nomura2020

fun main() {
	val (h1, m1, h2, m2, k) = readInts()
	val ans = (h2 * 60 + m2) - (h1 * 60 + m1) - k
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
