package codeforces.round637

private fun solve() {
	readLn()
	val a = readInts().map { it - 1 }
	println(a.good().iif("Yes", "No"))
}

private tailrec fun List<Int>.good(x: Int = 0): Boolean {
	if (x == size) return true
	val first = this[x]
	if ((first until size - x).any { this[x + it - first] != it }) return false
	return good(size - first)
}

fun main() = repeat(readInt()) { solve() }

private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
