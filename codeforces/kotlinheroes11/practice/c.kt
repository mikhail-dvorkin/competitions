package codeforces.kotlinheroes11.practice

private fun solve() {
	readln()
	val a = readInts()
	val ans = listOf(a[0], a.last()).minOf { v ->
		maxOf(a.size - a.takeWhile { it == v }.size - a.takeLastWhile { it == v }.size, 0)
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
