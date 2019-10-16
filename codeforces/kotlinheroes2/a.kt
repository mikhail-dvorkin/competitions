package codeforces.kotlinheroes2

fun main() {
	readLn()
	val a = readInts()
	val sel = a.toSet().sorted().take(3)
	if (sel.size < 3) return println("-1 -1 -1")
	println(sel.map { a.indexOf(it) + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
