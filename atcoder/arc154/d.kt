package atcoder.arc154

import kotlin.random.Random

fun main() {
	val n = readInt()
	val random = Random(566)
	val shuffle = (0 until n).shuffled(random)
	// p[i] + p[j] > p[k]
	fun ask(i: Int, j: Int, k: Int): Boolean {
		println("? ${shuffle[i] + 1} ${shuffle[j] + 1} ${shuffle[k] + 1}")
		return readLn()[0] == 'Y'
	}
	var one = 0
	for (i in 1 until n) {
		if (!ask(i, i, one)) {
			// 2p[i] <= p[one]
			one = i
		}
	}
	val a = ((0 until n) - one).toIntArray()
	val temp = IntArray(n)
	fun sort(from: Int, to: Int) {
		if (to - from < 2) return
		val mid = (from + to) / 2
		sort(from, mid)
		sort(mid, to)
		var i = from
		var j = mid
		var k = 0
		while (i < mid || j < to) {
			if (i < mid && (j == to || !ask(a[i], one, a[j]) /*a[i] < a[j]*/)) {
				temp[k++] = a[i++]
			} else {
				temp[k++] = a[j++]
			}
		}
		for (k in from until to) a[k] = temp[k - from]
	}
	sort(0, a.size)
	val ans = IntArray(n)
	ans[shuffle[one]] = 1
	for (i in a.indices) {
		ans[shuffle[a[i]]] = i + 2
	}
	print("! ${ans.joinToString(" ")}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
