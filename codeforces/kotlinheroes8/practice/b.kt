package codeforces.kotlinheroes8.practice

fun main() {
	val (a1, a2, k1, k2, n) = List(5) { readInt() }
	val min = maxOf(n - a1 * (k1 - 1) - a2 * (k2 - 1), 0)
	val max = (List(a1) { k1 } + List(a2) { k2 }).sorted().scan(0, Int::plus).indexOfLast { it <= n }
	println("$min $max")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
