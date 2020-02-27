package codeforces.kotlinheroes3.practice

fun main() {
	readLn()
	val s = readLn()
	println(s.indices.count { s.startsWith("xxx", it) })
}

private fun readLn() = readLine()!!
