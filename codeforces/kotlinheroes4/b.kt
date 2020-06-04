package codeforces.kotlinheroes4

fun main() = repeat(readInt()) {
	val (n, day1, day2) = readInts()
	val isFree = readLn().map { it == '0' }
	val a = IntArray(n)
	var took = 0
	for (i in isFree.indices) {
		if (isFree[i]) continue
		a[i] = day1
		if (i > 0) a[i] = minOf(a[i], day2 - a[i - 1])
		took += a[i]
	}
	println(took)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
