package codeforces.round584

fun main() {
	(0..readInt()).fold(readInts()) { a, i -> val x = a.minOrNull() ?: return println(i); a.filter { it % x != 0 } }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
