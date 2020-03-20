package codeforces.globalround7

private fun solve() {
	val s = readLn()
	var i = 0
	while (i < s.length - 1 - i && (s[i] == s[s.length - 1 - i])) i++
	println(s.take(i) + solve(s.substring(i, s.length - i)) + s.takeLast(i))
}

fun solve(s: String): String {
	val ss = s + s.reversed()
	val p = IntArray(ss.length + 1)
	val pow = IntArray(ss.length + 1)
	val M = 998244353
	val X = 567
	pow[0] = 1
	for (i in ss.indices) {
		p[i + 1] = (p[i] * X.toLong() % M + ss[i].toInt()).toInt()
		pow[i + 1] = ((pow[i] * X.toLong()) % M).toInt();
	}
	fun hash(from: Int, to: Int): Int {
		var r = (p[to] - p[from].toLong() * pow[to - from]) % M;
		if (r < 0) r += M
		return r.toInt()
	}
	var from = 0
	var to = 0
	for (i in 1..s.length) {
		if (hash(0, i) == hash(ss.length - i, ss.length)) {
			from = 0
			to = i
		}
		if (hash(s.length - i, s.length) == hash(s.length, s.length + i)) {
			from = s.length - i
			to = s.length
		}
	}
	return s.substring(from, to)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
