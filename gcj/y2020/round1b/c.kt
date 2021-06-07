package gcj.y2020.round1b

private fun greedy(init: List<Int>): String {
	var a = init
	val moves = mutableListOf<String>()
	while (a.zipWithNext().any { it.first > it.second }) {
		val pairs = mutableMapOf<Int, Pair<Int, Int>>()
		loop@for (i in 1 until a.size) for (j in i + 1 until a.size) {
			val score = sequenceOf(a[0] != a[j - 1], a[i - 1] == a[i], a[i - 1] != a[j], a[j - 1] == a[j]).count { it }
			pairs[score] = i to j
			if (score == 0) break@loop
		}
		val (i, j) = pairs[pairs.keys.minOrNull()]!!
		a = a.subList(i, j) + a.take(i) + a.drop(j)
		moves.add("$i ${j - i}")
	}
	return "${moves.size}\n${moves.joinToString("\n")}"
}

private fun solve(): String {
	val (r, s) = readInts()
	val a = List(s) { List(r) { it } }.flatten()
	return greedy(a)
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
