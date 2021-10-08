package codeforces.kotlinheroes8

fun main() {
	val (n, m) = readInts()
	val groups = List(2) { mutableListOf<IndexedValue<Int>>() }
	repeat(n) {
		val (k, t) = readInts()
		groups[t - 1].add(IndexedValue(it, k))
	}
	val groupSum = groups.map { group -> group.sumOf { it.value } }
	val can = BooleanArray(m + 1)
	val how = Array<IndexedValue<Int>?>(m + 1) { null }
	can[0] = true
	for (series in groups[1]) for (i in m - series.value downTo 0) {
		if (!can[i] || can[i + series.value]) continue
		can[i + series.value] = true
		how[i + series.value] = series
	}
	val x = can.indices.firstOrNull { can[it] && maxOf(2 * it - 1, 2 * (groupSum[1] - it)) + groupSum[0] <= m }
		?: return println(-1)
	val odd = mutableListOf<IndexedValue<Int>>()
	var z = x
	while (z > 0) z -= how[z]!!.also { odd.add(it) }.value
	val ans = IntArray(n)
	fun place(list: List<IndexedValue<Int>>, start: Int, gap: Int) {
		list.fold(start) { time, series -> (time + series.value * gap).also { ans[series.index] = time } }
	}
	place(odd, 1, 2)
	place(groups[1] - odd, 2, 2)
	place(groups[0], m - groupSum[0] + 1, 1)
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
