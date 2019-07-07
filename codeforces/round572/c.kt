package codeforces.round572

private const val M = 998244353

private fun solve() {
	val (n, m) = readInts()
	val a = readInts().sorted()
	val maxDiff = (a.last() - a.first()) / (m - 1)
	var ans = 0
	val d = List(n + 1) { IntArray(m) }
	for (diff in 1..maxDiff) {
		var iPrev = 0
		for (i in a.indices) {
			while (a[i] - a[iPrev] >= diff) iPrev++
			d[i + 1][0] = d[i][0].myPlus(1)
			for (j in 1 until m) {
				d[i + 1][j] = d[i][j].myPlus(d[iPrev][j - 1])
			}
		}
		ans = ans.myPlus(d[n][m - 1])
	}
	println(ans)
}

private fun Int.myPlus(other: Int): Int = (this + other) % M

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
