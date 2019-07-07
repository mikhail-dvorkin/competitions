package codeforces.round572

private fun solve() {
	val (_, p, k) = readInts()
	val groups = readInts().groupBy { x ->
		val x2 = 1L * x * x % p
		(1L * x * k - x2 * x2 % p + p) % p
	}
	println(groups.map { it.value.size }.map { it * (it - 1L) / 2 }.sum())
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
