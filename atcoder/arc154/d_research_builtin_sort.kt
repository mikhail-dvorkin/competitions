package atcoder.arc154

import java.util.*

fun main() {
	val n = readInt()
	// p[i] + p[j] > p[k]
	fun ask(i: Int, j: Int, k: Int): Boolean {
		println("? ${i + 1} ${j + 1} ${k + 1}")
		return readLn()[0] == 'Y'
	}

	var one = 0
	// 2p[i] <= p[one]
	for (i in 1 until n) if (!ask(i, i, one)) one = i

	val a = Array(n) { it }
	a[0] = one; a[one] = 0
	Arrays.sort(a, 1, n) { x, y -> if (ask(x, one, y)) 1 else -1 }

	val ans = IntArray(n)
	for (i in a.indices) ans[a[i]] = i + 1
	println("! ${ans.joinToString(" ")}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
