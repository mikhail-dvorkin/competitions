package facebook.y2019.round1

private fun solve(M: Int = 1_000_000_007): Int {
	val (n, k) = readInts()
	val s = readLn()
	val cost = IntArray(n) { 2 }
	for (i in 1 until n) { cost[i] = cost[i - 1] * 2 % M }
	var balance = 0
	var maxBalance = 0
	var ans = 0
	for (i in s.indices.reversed()) {
		balance += if (s[i] == 'A') 1 else -1
		if (maxBalance - balance > k) {
			balance += 2
			ans = (ans + cost[i]) % M
		}
		maxBalance = maxOf(maxBalance, balance)
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private val isOnlineJudge = System.getenv("ONLINE_JUDGE") == "true"
@Suppress("unused")
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
