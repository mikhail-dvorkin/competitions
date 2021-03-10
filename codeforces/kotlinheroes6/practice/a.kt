package codeforces.kotlinheroes6.practice

fun main() {
	readLn()
	val a = readInts()
	val unique = a.reversed().toSet().reversed()
	println(unique.size)
	println(unique.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
