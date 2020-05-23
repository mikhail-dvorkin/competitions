package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	readInt()
	val a = readInts()
	var (i, j) = 0 to a.lastIndex
	var prev = 0
	val sum = intArrayOf(0, 0)
	var moves = 0
	while (true) {
		var taken = 0
		while (taken <= prev && i <= j) taken += a[if (moves % 2 == 0) i++ else j--]
		if (taken == 0) break
		sum[moves++ % 2] += taken
		if (taken <= prev) break
		prev = taken
	}
	println("$moves ${sum.joinToString(" ")}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
