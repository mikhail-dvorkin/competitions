package codeforces.round572

private const val M = 998244353

private fun solve() {
	val (n, m) = readInts()
	val a = readInts().sorted()
	val maxDiff = (a.last() - a.first()) / (m - 1)
	val d = Array(m) { IntArray(n + 1) }
	var ans = 0
	for (diff in 1..maxDiff) {
		var iPrev = 0
		for (i in a.indices) {
			while (a[i] - a[iPrev] >= diff) iPrev++
			d[0][i + 1] = i + 1
			for (j in 1 until m) {
				d[j][i + 1] = (d[j][i] + d[j - 1][iPrev]) % M
			}
		}
		ans = (ans + d[m - 1][n]) % M
	}
	println(ans)
}

fun main() {
	val stdStreams = (false to true)
	val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
	if (!isOnlineJudge) {
		if (!stdStreams.first) System.setIn(java.io.File("input.txt").inputStream())
		if (!stdStreams.second) System.setOut(java.io.PrintStream("output.txt"))
	}
	val tests = if (isOnlineJudge) 1 else readInt()
	repeat(tests) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
