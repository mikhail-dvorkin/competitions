package gcj.y2022.round3

private fun solve(letters: String = "ACDEHIJKMORST"): String {
	val n = readInt()
	val exits = List(2) { readInts().map { it - 1 } }
	val dist1 = List(n) { v -> listOf(exits[0][v], exits[1][v]) }
	val dist2 = List(n) { v -> val d1 = dist1[v]; dist1[d1[0]] + dist1[d1[1]]	}
	val dist12 = List(n) { v -> dist1[v] + dist2[v] }
	for (i in 0 until n) {
		if (i in dist2[i]) return "IMPOSSIBLE"
	}
	val ans = IntArray(n) { -1 }
	val forbid = List(n) { mutableSetOf<Int>() }
	for (i in 0 until n) {
		val fi = forbid[i]
		for (j in dist12[i]) fi.add(ans[j])
		ans[i] = letters.indices.first { it !in fi }
		for (j in dist12[i]) forbid[j].add(ans[i])
	}
	return ans.map { letters[it] }.joinToString("")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
