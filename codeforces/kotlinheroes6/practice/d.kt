package codeforces.kotlinheroes6.practice

fun main() {
	val s = List(readInt()) { readLn() }.sortedBy { it.length }
	if (s.zipWithNext().any { !it.second.contains(it.first) }) return println("NO")
	println("YES")
	println(s.joinToString("\n"))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
