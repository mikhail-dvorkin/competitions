package codeforces.round637

fun main() {
	readLn()
	val d = readInts().sorted()
	val (g, r) = readInts()

	val inf = Int.MAX_VALUE
	val dist = Array(g) { IntArray(d.size) { inf } }
	val queue = Array(g) { IntArray(d.size) }
	val queueLow = IntArray(g)
	val queueHigh = IntArray(g)
	dist[0][0] = 0
	queueHigh[0] = 1
	val dLast = d.last()
	val maxMod = d.map { g + it - dLast }.toIntArray()

	var time = -1; var mod = -1
	fun process(i2: Int, add: Int) {
		var mod2 = mod + add
		if (mod2 > g) return
		var time2 = time + add
		if (mod2 == g) { time2 += r; mod2 = 0 }
		if (dist[mod2][i2] != inf) return
		dist[mod2][i2] = time2
		queue[mod2][queueHigh[mod2]++] = i2
	}

	var ans = inf
	while (time < ans) {
		time++; mod++
		if (mod == g) { time += r; mod = 0; if (queueLow[0] == queueHigh[0]) break }
		val queueMod = queue[mod]
		repeat(queueHigh[mod] - queueLow[mod]) {
			val i = queueMod[queueLow[mod]++]
			val di = d[i]
			if (mod <= maxMod[i]) ans = minOf(ans, time + dLast - di)
			if (i > 0) process(i - 1, di - d[i - 1])
			if (i + 1 < d.size) process(i + 1, d[i + 1] - di)
		}
	}
	println(if (ans < inf) ans else -1)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
