package codeforces.round576

private fun solve() {
	val (n, m) = readInts()
	val taken = BooleanArray(3 * n)
	val matching = mutableListOf<Int>()
	for (i in 0 until m) {
		val (a, b) = readInts().map { it - 1 }
		if (taken[a] || taken[b]) continue
		taken[a] = true
		taken[b] = true
		matching.add(i)
	}
	val ans = if (matching.size >= n) {
		Pair("Matching", matching)
	} else {
		Pair("IndSet", taken.indices.filter { !taken[it] })
	}
	out.println(ans.first)
	out.println(ans.second.take(n).map { it + 1 }.joinToString(" "))
}

private val out = java.io.PrintWriter(System.out)

fun main() {
	repeat(readInt()) { solve() }
	out.close()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
