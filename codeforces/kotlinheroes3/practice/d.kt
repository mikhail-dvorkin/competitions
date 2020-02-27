package codeforces.kotlinheroes3.practice

fun main() {
	val w = readInts()[1]
	val balances = readInts().fold(mutableListOf(0), { a, v -> a.apply { add(a.last() + v) } })
	println(maxOf(w - balances.max()!! + balances.min()!! + 1, 0))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
