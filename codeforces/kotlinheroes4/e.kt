package codeforces.kotlinheroes4

fun main() {
	val (n, m, sIn) = readInts()
	val s = sIn - 1
	val swaps = List(m) { readInts().map { it - 1 } }
	val inf = m + 1
	val a = IntArray(n) { inf }
	a[s] = 0
	for ((x, y) in swaps) {
		val t = a[x]
		a[x] = a[y]
		a[y] = t
		a[x] = minOf(a[x], a[y] + 1)
		a[y] = minOf(a[y], a[x] + 1)
	}
	println(a.map { if (it < inf) it else -1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
