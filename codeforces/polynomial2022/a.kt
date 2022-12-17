package codeforces.polynomial2022

private fun solve() {
	readln()
	val s = readln()
	var sign = false
	for (i in s.indices) {
		if (i > 0) print(if (sign) "-" else "+")
		if (s[i] == '1') sign = !sign
	}
	println()
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
