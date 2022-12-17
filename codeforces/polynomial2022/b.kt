package codeforces.polynomial2022

private fun solve(): String {
	val (n, _, k) = readInts()
	val a = readInts().sortedDescending()
	for (i in a.indices) {
		if (i >= k) continue
		val allowed = n / k + (if (i < n % k) 1 else 0)
		if (a[i] > allowed) return "NO"
	}
	return "YES"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
