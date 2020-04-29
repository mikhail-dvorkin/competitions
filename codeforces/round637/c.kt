package codeforces.round637

fun main() {
	readLn()
	val d = readInts().sorted()
	val (g, r) = readInts()
	val inf = Int.MAX_VALUE
	val mark = Array(g) { BooleanArray(d.size) { false } }
	val queue = Array(g) { IntArray(d.size) }
	val queueLow = IntArray(g)
	val queueHigh = IntArray(g)
	mark[0][0] = true
	queueHigh[0] = 1
	var ans = inf
	var time = 0
	while (time < ans) {
		val mod = time % (g + r)
		repeat(queueHigh[mod] - queueLow[mod]) {
			val i = queue[mod][queueLow[mod]++]
			if (mod + d.last() - d[i] <= g) ans = minOf(ans, time + d.last() - d[i])
			for (i2 in intArrayOf(i - 1, i + 1)) {
				val mod2 = (((d.getOrNull(i2) ?: continue) - d[i]).abs() + mod)
						.let { if (it == g) 0 else it }
				if (mod2 > g || mark[mod2][i2]) continue
				mark[mod2][i2] = true
				queue[mod2][queueHigh[mod2]++] = i2
			}
		}
		if (++time % (g + r) == g) {
			time += r
			if (queueLow[0] == queueHigh[0]) break
		}
	}
	println(if (ans < inf) ans else -1)
}

private fun Int.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
