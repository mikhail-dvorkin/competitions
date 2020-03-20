package codeforces.globalround7

fun main() {
	val n = readInt()
	val p = readInts().map { it - 1 }
	val q = readInts().map { it - 1 }
	val pRev = IntArray(n)
	for (i in p.indices) pRev[p[i]] = i

	val st = SegmentsTreeSimple(2 * n)
	var alive = n
	val ans = q.map {
		while (st.getMinBalance() >= 0) st[2 * n - 2 * pRev[--alive] - 1] = -1
		st[2 * n - 2 * it - 2] = 1
		alive
	}
	println(ans.map{ it + 1 }.joinToString(" "))
}

class SegmentsTreeSimple(var n: Int) {
	var size = 2 * Integer.highestOneBit(n)
	var min = IntArray(2 * size)
	var sum = IntArray(2 * size)

	operator fun set(index: Int, value: Int) {
		var i = size + index
		min[i] = minOf(0, value)
		sum[i] = value
		while (i > 1) {
			i /= 2
			min[i] = minOf(min[2 * i], sum[2 * i] + min[2 * i + 1])
			sum[i] = sum[2 * i] + sum[2 * i + 1]
		}
	}

	fun getMinBalance(): Int = min[1]
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
