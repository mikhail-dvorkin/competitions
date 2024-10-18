package facebook.y2024.round1

private fun solve(): Double {
	val n = readInt()
	val pairs = List(n) { readInts().toPair() }
	val minSpeed = pairs.indices.maxOf { i ->
		val dist = i + 1
		val time = pairs[i].second
		dist * 1.0 / time
	}
	for (i in pairs.indices) {
		val dist = i + 1
		val time = dist / minSpeed
		if (time + 1e-9 < pairs[i].first) return -1.0
	}
	return minSpeed
}

private fun <T> List<T>.toPair() = get(0) to get(1)
private val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
private val out = System.out.bufferedWriter()

fun main() {
	val tests = readInt()
	val startTime = System.currentTimeMillis()
	var prevTime = startTime
	repeat(tests) {
		println("Case #${it + 1}: ${solve()}")
		val curTime = System.currentTimeMillis()
		if (curTime > prevTime + 1000) {
			System.err.println("${((it + 1) * 100.0 / tests).toInt()}%\t${(curTime - startTime) / 1000.0}s")
			prevTime = curTime
		}
	}
	out.close()
}
