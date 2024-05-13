package codeforces.kotlinheroes10.practice

private fun solve(g: Char = 'g') {
	val c = readln().last()
	val s = readln()
	if (c == g) return println(0)
	val ss = s + s
	var nextG = -1
	var ans = 0
	for (i in ss.indices.reversed()) {
		if (ss[i] == g) nextG = i
		if (ss[i] == c && nextG != -1) ans = maxOf(ans, nextG - i)
	}
	println(ans)
}

private fun readInt() = readln().toInt()

fun main() = repeat(readInt()) { solve() }
