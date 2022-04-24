package codeforces.globalround20

private fun solve(): Boolean {
	val s = readLn().reversed()
	if (s.startsWith("A")) return false
	var balance = 0
	for (c in s) {
		if (c == 'A') {
			balance--
			if (balance < 0) balance = 0
		} else {
			balance++
		}
	}
	return balance == 0
}

fun main() = repeat(readInt()) { println(if (solve()) "YES" else "NO") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
