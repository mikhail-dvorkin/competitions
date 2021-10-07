package codeforces.kotlinheroes8.practice

fun main() {
	val (hei, wid) = readInts()
	val a = List(hei) { readInts().toIntArray() }
	val moves = mutableListOf<Pair<Int, Int>>()
	for (i in 0..hei - 2) for (j in 0..wid - 2) {
		val square = (i..i+1).cartesianProduct(j..j+1)
		if (square.any { a[it.first][it.second] == 0 }) continue
		square.forEach { a[it.first][it.second] = 2 }
		moves.add(i to j)
	}
	if (a.any { it.contains(1) }) return println(-1)
	println(moves.size)
	println(moves.joinToString("\n") { "${it.first + 1} ${it.second + 1}" })
}

private fun <T, R> Iterable<T>.cartesianProduct(other: Iterable<R>) = flatMap { x -> other.map { y -> x to y } }
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
