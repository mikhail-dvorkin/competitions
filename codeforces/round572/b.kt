package codeforces.round572

private fun solve() {
	val (n, p, k) = readInts()
	val a = readInts()
	val count = mutableMapOf<Int, Int>()
	for (x in a) {
		val x2 = 1L * x * x % p
		val x4 = 1L * x2 * x2 % p
		val h = (((x4 - k * 1L * x) % p + p) % p).toInt()
		count[h] = count.getOrDefault(h, 0) + 1
	}
	var ans = 0L
	for (v in count.values) {
		ans += v * 1L * (v - 1) / 2
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
