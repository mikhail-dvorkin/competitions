package codeforces.kotlinheroes8

private fun solve() {
	var minK = 0
	var maxK = Int.MAX_VALUE
	repeat(readInt()) {
		val (s, t, shouldIn) = readStrings()
		val sr = s.reversed()
		val tr = t.reversed()
		val should = shouldIn.toInt() > 0
		var x = 0
		while (x < sr.length && x < tr.length && sr[x] == tr[x]) x++
		if (should) {
			maxK = minOf(maxK, x)
		} else {
			minK = maxOf(minK, x + 1)
		}
	}
	val ans = (minK..maxK).toList()
	println(ans.size)
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
