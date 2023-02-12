package atcoder.arc154

import kotlin.random.Random

fun main() {
	val n = readInt()
	val random = Random(566)
	val shuffle = (0 until n).shuffled(random)
	val memo = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
	// p[i] + p[j] > p[k]
	fun ask(i: Int, j: Int, k: Int): Boolean {
		if (i > j) return ask(j, i, k)
		return memo.getOrPut(Triple(i, j, k)) {
			println("? ${shuffle[i] + 1} ${shuffle[j] + 1} ${shuffle[k] + 1}")
			readLn()[0] == 'Y'
		}
	}

	var one = 0
	// 2p[i] <= p[one]
	for (i in 1 until n) if (!ask(i, i, one)) one = i

	val a = IntArray(n) { it }
	a[0] = one; a[one] = 0
	val mergeSortTemp = IntArray(n)
	fun sort(from: Int, to: Int) {
		val partSize = to - from
		if (partSize <= 1) return
		val mid = (from + to) / 2
		sort(from, mid); sort(mid, to)
		var i = from; var j = mid
		for (k in 0 until partSize) mergeSortTemp[k] = a[
			if (i < mid && (j == to || !ask(a[i], one, a[j]))) i++ else j++
		]
		System.arraycopy(mergeSortTemp, 0, a, from, partSize)
	}
	sort(1, n)

	val ans = IntArray(n)
	for (i in a.indices) {
		ans[shuffle[a[i]]] = i + 1
	}
	println("! ${ans.joinToString(" ")}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
