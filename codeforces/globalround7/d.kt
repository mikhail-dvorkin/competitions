package codeforces.globalround7

private fun solve() {
	val s = readLn()
	var i = 0
	while (i < s.length - 1 - i && s[i] == s[s.length - 1 - i]) i++
	println(s.take(i) + solve(s.drop(i).dropLast(i)) + s.takeLast(i))
}

private fun solve(s: String): String {
	val n = s.length
	val h = Hashing(s + s.reversed())
	for (i in n downTo 0) {
		if (h.hash(0, i) == h.hash(2 * n - i, 2 * n)) return s.take(i)
		if (h.hash(n - i, n) == h.hash(n, n + i)) return s.takeLast(i)
	}
	error(s)
}

private const val M = 1_000_000_007

class Hashing(s: String, x: Int = 566239) {
	val h = IntArray(s.length + 1)
	val t = IntArray(s.length + 1)

	fun hash(from: Int, to: Int): Int {
		var res = ((h[to] - h[from] * t[to - from].toLong()) % M).toInt()
		if (res < 0) res += M
		return res
	}

	init {
		t[0] = 1
		for (i in s.indices) {
			t[i + 1] = (t[i] * x.toLong() % M).toInt()
			h[i + 1] = ((h[i] * x.toLong() + s[i].toLong()) % M).toInt()
		}
	}
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
