package codeforces.kotlinheroes6.practice

fun main() {
	readLn()
	val s = readLn()
	println(s.windowed(3).count { it == "xxx" })
}

private fun readLn() = readLine()!!
