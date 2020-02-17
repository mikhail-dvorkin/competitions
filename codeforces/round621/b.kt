package codeforces.round621

private fun solve(): Int {
	val (_, x) = readInts()
	val a = readInts()
	val s = a.max()!!
	if (x % s == 0) return x / s
	if (a.contains(x)) return 1
	return maxOf(x / s + 1, 2)
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
