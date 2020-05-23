package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val s = readLn().reversed()
	val ans = s.mapIndexedNotNull { index, c -> (c + "0".repeat(index)).takeIf { c > '0' } }
	println(ans.size)
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
