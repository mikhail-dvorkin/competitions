package codeforces.globalround7

fun main() {
	readLn()
	var max = 0
	val a = readInts().map { (it + max).also { v -> max = maxOf(max, v) } }
	println(a.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
