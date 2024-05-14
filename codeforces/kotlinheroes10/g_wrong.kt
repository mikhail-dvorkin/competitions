package codeforces.kotlinheroes10

private fun solve() {
	val (n, _) = readInts()
	val xs = readInts()
	val hs = readInts()
	val right = IntArray(n + 1) { -1 }
	val left = IntArray(n + 1) { -1 }
	val furthestRight = IntArray(n + 1)
	for (i in xs.indices) {
		val a = maxOf(xs[i] - hs[i], 0)
		val b = minOf(xs[i] + hs[i], n)
		right[a] = maxOf(right[a], b)
		left[b] = maxOf(left[b], a)
	}
	val solveSingleLeft = IntArray(n + 1)
	var cover = - 2 * n
	for (i in 0..n) {
		if (right[i] != -1) cover = maxOf(cover, right[i])
		furthestRight[i] = cover
		solveSingleLeft[i] = maxOf(i - cover, 0)
	}
	val solveSingleRight = IntArray(n + 1)
	val solveSingle = IntArray(n + 1)
	cover = 3 * n
	for (i in n downTo 0) {
		if (left[i] != -1) cover = minOf(cover, left[i])
		solveSingleRight[i] = maxOf(cover - i, 0)
		solveSingle[i] = minOf(solveSingleLeft[i], solveSingleRight[i])
	}
	fun solve(qLeft: Int, qRight: Int): Int {
		if (solveSingle[qLeft] == 0) return solveSingle[qRight]
		if (solveSingle[qRight] == 0) return solveSingle[qLeft]
		var ans = solveSingle[qLeft] + solveSingle[qRight]
		if (solveSingleLeft[qRight] < 2 * n && solveSingleLeft[qRight] > qRight - qLeft) {
			ans = minOf(ans, solveSingleLeft[qRight])
		}
		if (solveSingleRight[qLeft] < 2 * n && solveSingleRight[qLeft] > qRight - qLeft) {
			ans = minOf(ans, solveSingleRight[qLeft])
		}
		if (solveSingleLeft[qRight] < 2 * n && solveSingleLeft[qRight] < qRight - qLeft) {
			val lowPay = 0
			val highPay = qRight - qLeft
			fun okPay(pay: Int) = furthestRight[qLeft + pay] + pay >= qRight
			val pay = (lowPay..highPay).binarySearch(::okPay)
			ans = minOf(ans, pay)
		}
		return ans
	}
	repeat(readInt()) {
		val (qLeft, qRight) = readInts()
		out.append(solve(qLeft, qRight).toString())
		out.append(" ")
	}
}

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

private val out = System.out.bufferedWriter()

fun main() = repeat(1) { solve() }
	.also { out.close() }
