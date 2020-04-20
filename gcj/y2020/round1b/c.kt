package gcj.y2020.round1b

private fun greedy(init: List<Int>): String {
	var a = init
	val moves = mutableListOf<String>()
	while (true) {
		if (a.zipWithNext().all { it.first <= it.second }) {
			return moves.size.toString() + "\n" + moves.joinToString("\n")
		}
		val pairs = mutableMapOf<Int, Pair<Int, Int>>()
		for (i in 1 until a.size) {
			for (j in i + 1..a.size) {
				var score = 0
				if (a[0] == a[j - 1]) score++
				if (j < a.size && a[i - 1] == a[j]) score++
				if (j == a.size || a[j - 1] == a[j]) score--
				if (a[i - 1] == a[i]) score--
				pairs[score] = i to j
				if (2 in pairs) break
			}
			if (2 in pairs) break
		}
		val (i, j) = pairs[pairs.keys.max()]!!
		a = a.subList(i, j) + a.take(i) + a.drop(j)
		moves.add("$i ${j - i}")
	}
}

private fun solve(r: Int, s: Int): String {
	val a = List(s) { List(r) { it } }.flatten()
	return greedy(a)
}

fun main() = repeat(readInt()) {
	val (r, s) = readInts()
	println("Case #${it + 1}: ${solve(r, s)}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
