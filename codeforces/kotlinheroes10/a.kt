package codeforces.kotlinheroes10

private fun solve(): Int {
	val n = readInt()
	if (n < 3) return n
	if (n == 4 || n == 7) return 1
	return 0
}

private fun readInt() = readln().toInt()

fun main() = repeat(readInt()) { println(solve()) }
