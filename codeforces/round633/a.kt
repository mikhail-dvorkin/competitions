package codeforces.round633

private fun solve() {
	readLn()
	val a = readInts()
	var max = Int.MIN_VALUE
	var maxUp = 0
	for (x in a) {
		max = maxOf(max, x)
		maxUp = maxOf(maxUp, max - x)
	}
	if (maxUp == 0) return println(0)
	println((0..32).first { (1L shl it) > maxUp })
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
