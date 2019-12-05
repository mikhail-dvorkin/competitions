package codeforces.globalround5

private fun solve(n: Int, m: Int = 1): Int {
	if (m == n || m + 1 == n) return 1
	if (m > n) return 0
	return solve(n, 2 * m + 1 + m % 2)
}

fun main() = println(solve(readInt()))

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
