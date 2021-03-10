package codeforces.kotlinheroes6.practice

private fun solve() {
	val (n, a, bIn) = readLongs()
	val b = minOf(bIn, 2 * a)
	println(n / 2 * b + n % 2 * a)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
