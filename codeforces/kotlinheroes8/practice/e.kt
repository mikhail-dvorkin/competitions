package codeforces.kotlinheroes8.practice

private fun solve() {
	val s = readLn()
	println(s.dropWhile { it == '0' }.dropLastWhile { it == '0' }.count { it == '0' })
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
