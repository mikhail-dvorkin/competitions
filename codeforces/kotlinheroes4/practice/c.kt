package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val n = readInt().toString()
	val ans = n.mapIndexedNotNull { index, c ->
		if (c == '0') return@mapIndexedNotNull null
		c.toString() + List(n.length - index - 1) { "0" }.joinToString("")
	}
	println(ans.size)
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
