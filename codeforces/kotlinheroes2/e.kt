package codeforces.kotlinheroes2

fun main() {
	readLine()
	val a = readInts().map { it - 1 }
	var low = 0
	var high = a.size / 2 + 1
	while (low + 1 < high) {
		val mid = (low + high) / 2
		if (solve(a, mid) != null) low = mid else high = mid
	}
	println(solve(a, low))
}

private fun solve(a: List<Int>, m: Int): String? {
	val count = IntArray(m)
	val ans = CharArray(a.size) {'B'}
	val b = mutableListOf<Int>()
	val c = mutableListOf<Int>()
	for ((index, v) in a.withIndex()) {
		if (v >= m) continue
		if (count[v] == 0) {
			ans[index] = 'R'
			b.add(v)
		} else if (count[v] == 1) {
			ans[index] = 'G'
			c.add(v)
		}
		count[v]++
	}
	return String(ans).takeIf { count.all { it == 2 } && b == c }
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
