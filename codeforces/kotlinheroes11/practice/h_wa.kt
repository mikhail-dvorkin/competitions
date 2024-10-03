package codeforces.kotlinheroes11.practice

private fun solve(): Int {
	val (_, x) = readInts()
	val a = readInts().map { it - 1 }
	if (a.all { it == a[0] }) return 0
	val inf = Int.MAX_VALUE / 4
	val rightmost = List(x) { IntArray(a.size) { -inf } }
	for (i in a.indices) {
		if (i > 0) for (j in 0 until x) rightmost[j][i] = rightmost[j][i - 1]
		rightmost[a[i]][i] = i
	}
	val leftmost = List(x) { IntArray(a.size) { inf } }
	for (i in a.indices.reversed()) {
		if (i < a.lastIndex) for (j in 0 until x) leftmost[j][i] = leftmost[j][i + 1]
		leftmost[a[i]][i] = i
	}

	val memo = mutableMapOf<Int, Int>()
	fun solve(from: Int, to: Int): Int = memo.getOrPut((from shl 16) + to) {
		require(a[from] == a[to - 1])
		var result = inf
		for (c in 0 until x) {
			if (c == a[from]) continue
			val p = leftmost[c][from]
			if (p >= to) return 1
			val q = rightmost[c][to - 1]
			result = minOf(result, solve(p, q + 1) + 1)
		}
//		for (i in from..to - 2) {
//			if (a[i] == a[from] && a[i + 1] != a[from]) {
//				val j = leftmost[a[from]][i + 1]
//				result = minOf(result, solve(from, i + 1) + solve(j, to))
//			}
//		}
		return result
	}
	var answer = inf
	for (c in 0 until x) {
		var from = 0
		while (a[from] == c) from++
		var to = a.size
		while (a[to - 1] == c) to--
		val p = leftmost[c][from]
		if (p >= to) return 1
		val q = rightmost[c][to - 1]
		answer = minOf(answer, solve(p, q + 1) + 1)
	}
	return answer
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
