package facebook.y2023.round1

const val S = 41

private fun precalc(): List<List<MutableMap<Int, Int>>> {
	val dp = List(S + 1) { List(it + 1) { mutableMapOf<Int, Int>() } }
	dp[0][0][1] = -1
	for (sum in 0 until S) {
		for (count in 0..sum) {
			for (key in dp[sum][count].keys) {
				for (x in 1..S - sum) {
					dp[sum + x][count + 1][key * x] = x
				}
			}
		}
	}
	return dp
}

val precalc = precalc()

private fun solve(): List<Int> {
	val p = readInt()
	for (count in 1..S) {
		if (p in precalc[S][count]) {
			val list = mutableListOf<Int>()
			var pp = p
			var ss = S
			for (c in count downTo 1) {
				val x = precalc[ss][c][pp]!!
				list.add(x)
				ss -= x
				pp /= x
			}
			return listOf(list.size) + list
		}
	}
	return listOf(-1)
}

fun main() {
	precalc()
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve().joinToString(" ")}") }
}

private fun readInt() = readln().toInt()
