package codeforces.round626

fun main() {
	readLn()
	val s = readLn()
	var bal = 0
	var ans = 0
	var mode = 0
	var pos = 0
	for (i in s.indices) {
		bal += if (s[i] == '(') 1 else -1
		if (bal == 0) {
			if (mode < 0) {
				ans += i - pos
			}
			mode = 0
		} else if (mode == 0) {
			mode = bal
			pos = i - 1
		}
	}
	println(if (bal != 0) -1 else ans)
}

private fun readLn() = readLine()!!
