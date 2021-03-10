package codeforces.kotlinheroes6.practice

fun main() {
	val (_, m, k) = readInts()
	val a = readInts()
	var last = a.size
	repeat(m) {
		var taken = 0
		var i = last - 1
		while (i >= 0 && taken + a[i] <= k) taken += a[i--]
		last = i + 1
	}
	println(a.size - last)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
