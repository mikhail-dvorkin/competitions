package codeforces.kotlinheroes4

fun main() {
	val (n, m, sIn) = readInts()
	val s = sIn - 1
	val inf = m + 1
	val a = IntArray(n) { inf }.also { it[s] = 0 }
	repeat(m) {
		val (x, y) = readInts().map { it - 1 }
		val new = minOf(a[y], a[x] + 1) to minOf(a[x], a[y] + 1)
		a[x] = new.first; a[y] = new.second
	}
	println(a.map { if (it < inf) it else -1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
