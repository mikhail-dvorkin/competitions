package codeforces.educationalround95

private fun solve() {
	val n = readInt()
	val a = readInts()
	val b = IntArray(n + 1) { n + 1 }
	b[0] = 0
	for (i in 0 until n) {
		for (j in 1..2) {
			if (i + j == n) b[n] = minOf(b[n], b[i] + a.subList(i, n).sum())
			for (k in 1..2) {
				if (i + j + k > n) continue
				b[i + j + k] = minOf(b[i + j + k], b[i] + a.subList(i, i + j).sum())
			}
		}
	}
	println(b.last())
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
