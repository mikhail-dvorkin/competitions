package codeforces.kotlinheroes8

fun main() {
	val (n, m) = readInts()
	val consecutive = mutableListOf<IndexedValue<Int>>()
	val alternating = mutableListOf<IndexedValue<Int>>()
	repeat(n) {
		val (k, t) = readInts()
		(if (t == 1) consecutive else alternating).add(IndexedValue(it, k))
	}
	val consecutiveSum = consecutive.sumOf { it.value }
	val alternatingSum = alternating.sumOf { it.value }
	val can = BooleanArray(m + 1)
	val how = MutableList<IndexedValue<Int>?>(m + 1) { null }
	can[0] = true
	for (a in alternating) {
		for (i in m - a.value downTo 0) {
			if (!can[i] || can[i + a.value]) continue
			can[i + a.value] = true
			how[i + a.value] = a
		}
	}
	for (x in 0..m) {
		if (!can[x]) continue
		val odd = 2 * x - 1
		val even = 2 * (alternatingSum - x)
		val ans = maxOf(odd, even) + consecutiveSum
		if (ans <= m) {
			val starts = IntArray(n)
			var z = x
			val odds = mutableListOf<IndexedValue<Int>>()
			while (z > 0) {
				odds.add(how[z]!!)
				z -= how[z]!!.value
			}
			val evens = alternating - odds
			var t = 1
			for (a in odds) {
				starts[a.index] = t
				t += a.value * 2
			}
			t = 2
			for (a in evens) {
				starts[a.index] = t
				t += a.value * 2
			}
			t = m + 1
			for (a in consecutive) {
				t -= a.value
				starts[a.index] = t
			}
			return println(starts.joinToString(" "))
		}
	}
	println(-1)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
