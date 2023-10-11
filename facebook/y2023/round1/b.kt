package facebook.y2023.round1

private fun precalc(s: Int = 41): List<MutableMap<Int, Sequence<Int>>> {
	val dp = List(s + 1) { List(it + 1) { mutableMapOf<Int, Sequence<Int>>() } }
	dp[0][0][1] = emptySequence()
	for (sum in 0 until s) {
		for (count in 0..sum) {
			for (entry in dp[sum][count]) {
				for (x in 1..s - sum) {
					dp[sum + x][count + 1][entry.key * x] = sequenceOf(x) + entry.value
				}
			}
		}
	}
	return dp[s]
}

private val precalc = precalc()

private fun solve(): List<Int> {
	val p = readInt()
	val map = precalc.firstOrNull { p in it }
		?: return listOf(-1)
	return map[p]!!.toList().let { listOf(it.size) + it }
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve().joinToString(" ")}") }
}

private fun readInt() = readln().toInt()
