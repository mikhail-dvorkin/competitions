package codeforces.kotlinheroes8

private fun solve(): String {
	val s = readLn()
	if (s.all { it == '=' }) return "="
	if (s.none { it == '>' }) return "<"
	if (s.none { it == '<' }) return ">"
	return "?"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
