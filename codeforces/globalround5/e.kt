package codeforces.globalround5

private fun solve(n: Int): Int {
	if (n < 3) return 1
	var m = 4
	var mode = 0
	while (m <= n) {
		if (m == n || m + 1 == n) return 1
		m = 2 * m + 1 + mode
		mode = 1 - mode
	}
	return 0
}

fun main() = println(solve(readInt()))

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
