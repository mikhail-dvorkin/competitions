package codeforces.kotlinheroes4.practice

fun main() = repeat(readInt()) {
	val n = readInt()
	if (n < 4) return@repeat println("-1")
	val ans = (n downTo 5).filter { it % 2 == 0 } + listOf(3, 1, 4, 2) + (5..n).filter { it % 2 == 1 }
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
