package codeforces.round572

private fun solve() {
	val n = readInt()
	val deg = IntArray(n)
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		deg[a]++
		deg[b]++
	}
	println(if (deg.contains(2)) "NO" else "YES")
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
