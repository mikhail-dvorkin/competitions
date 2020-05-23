package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	readInt()
	val a = readInts()
	var i = 0
	var j = a.lastIndex
	var prev = 0
	var sumA = 0
	var sumB = 0
	var moves = 0
	while (true) {
		var taken = 0
		while (taken <= prev && i <= j) {
			taken += a[i++]
		}
		if (taken == 0) break
		moves++
		sumA += taken
		if (taken <= prev) break
		prev = taken

		taken = 0
		while (taken <= prev && i <= j) {
			taken += a[j--]
		}
		if (taken == 0) break
		moves++
		sumB += taken
		if (taken <= prev) break
		prev = taken
	}
	println("$moves $sumA $sumB")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
