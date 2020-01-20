package codeforces.round614

fun main() {
	val (n, m) = readInts()
	val a = List(2) { BooleanArray(n) }
	var obstacles = 0
	repeat(m) {
		val (r, c) = readInts().map { it - 1 }
		a[r][c] = !a[r][c]
		for (d in (c - 1..c + 1).intersect(0 until n)) {
			if (a[r xor 1][d]) {
				obstacles += if (a[r][c]) 1 else -1
			}
		}
		println(if (obstacles > 0) "No" else "Yes")
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
