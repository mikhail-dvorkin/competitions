package codeforces.round616

fun main() {
	val s = readLn()
	val pref = ('a'..s.max()!!).map { c -> s.map { it == c }.prefixSum() }
	val ans = List(readInt()) {
		val (lowIn, high) = readInts()
		val low = lowIn - 1
		val count = pref.indices.count { c -> pref[c][low] != pref[c][high] }
		count >= 3 || count == 2 && s[low] != s[high - 1] || high - low == 1
	}
	println(ans.joinToString("\n") { if (it) "Yes" else "No" })
}

private fun Iterable<Boolean>.prefixSum(): List<Int> {
	return mutableListOf(0).also { a -> this.forEach { a.add(a.last() + if (it) 1 else 0 ) } }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
