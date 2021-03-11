package codeforces.kotlinheroes6

private fun solve() {
	val (_, k) = readInts()
	val s = readLn()
	var bal = 0
	var prev = -1
	var possible2 = 0
	var possible1 = 0
	for (i in s.indices) {
		bal += if (s[i] == '(') 1 else -1
		if (bal == 0) {
			val len = i - prev
			possible2 += len / 2 - 1
			prev = i
			possible1++
		}
	}
	println(possible1 + minOf(possible2, k))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
