package codeforces.polynomial2022

import java.lang.StringBuilder

private fun solve() {
	val ans = StringBuilder()
	val n = readInt()
	val types = readln().map { it - '0' }
	var last = -1
	var lastSame = 0
	for (x in 2..n) {
		if (types[x - 2] == last) {
			lastSame++
		} else {
			last = types[x - 2]
			lastSame = 1
		}
		ans.append(x - lastSame).append(" ")
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
