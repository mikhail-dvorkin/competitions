package atcoder.arc128

import java.util.*

fun main() {
	val (_, k) = readInts()
	val a = readInts().toIntArray()
	data class Option(val value: Int, val amount: Int)
	val options = TreeSet<Option>(compareBy({ it.amount }, { it.value }))
	for (x in a.indices) {
		options.add(Option(x, a[x]))
	}
	val length = a.sum()
	val ans = IntArray(length)
	for (i in ans.indices) {
		if (i >= k) {
			val x = ans[i - k]
			if (a[x] > 0) {
				options.add(Option(x, a[x]))
			}
		}
		val selected = options.pollLast() ?: return println(-1)
		ans[i] = selected.value
		a[selected.value]--
	}
	println(ans.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
