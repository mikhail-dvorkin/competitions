package atcoder.arc158

import kotlin.math.abs

private fun solve(): Int {
	val (a, b, c) = readInts().sorted()
	if (listOf(a, b, c).map { it % 2 }.toSet().size != 1) return -1
	val (x, y, z) = listOf(a, b, c).map { it / 2 }
	if ((x + y + z) % 3 != 0) return -1
	val av = (x + y + z) / 3
	return (listOf(x, y, z).sumOf { abs(it - av) } / 2)
}

private inline fun <T> Iterable<T>.sumOf(selector: (T) -> Int): Int {
	var sum: Int = 0.toInt()
	for (element in this) {
		sum += selector(element)
	}
	return sum
}

fun main() {
	println(List(readInt()) { solve() }.joinToString("\n"))
}

private val bufferedReader = java.io.BufferedReader(java.io.InputStreamReader(System.`in`))
private fun readLn() = bufferedReader.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
