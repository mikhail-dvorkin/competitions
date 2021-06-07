package codeforces.globalround10

private fun solve() {
	val k = (readStrings()[1].toLong() + 1) % 2 + 1
	var a = readInts()
	repeat(k.toInt()) {
		val d = a.maxOrNull()!!
		a = a.map { d - it }
	}
	println(a.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
