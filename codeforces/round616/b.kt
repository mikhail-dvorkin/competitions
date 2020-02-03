package codeforces.round616

import java.lang.StringBuilder

fun main() {
	val s = readLn()
	val p = List(26) { i ->
		val c = 'a' + i
		val a = mutableListOf(0)
		for (j in s) {
			a.add(a.last() + (if (j == c) 1 else 0))
		}
		a
	}
	val sb = StringBuilder()
	repeat(readInt()) {
		val (lowIn, high) = readInts()
		val low = lowIn - 1
		val count = p.indices.count { i -> p[i][low] != p[i][high] }
		if (count >= 3 || count == 2 && s[low] != s[high - 1] || high - low == 1) {
			sb.append("Yes\n")
		} else {
			sb.append("No\n")
		}
	}
	print(sb)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
