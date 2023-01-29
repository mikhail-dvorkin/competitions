package atcoder.arc155

import java.util.TreeSet

fun main() {
	val (q, a0, b0) = readInts()
	val ts = TreeSet<Int>()
	ts.add(Int.MIN_VALUE)
	ts.add(Int.MAX_VALUE)
	ts.add(a0 - b0)
	ts.add(a0 + b0)
	repeat(q) {
		val (type, a, b) = readInts()
		if (type == 1) {
			ts.add(a - b)
			ts.add(a + b)
			return@repeat
		}
		val y = ts.ceiling(a)!!
		if (y in a..b) return@repeat println(0)
		val x = ts.floor(a)!!
		if (y == Int.MAX_VALUE) return@repeat println(a - x)
		if (x == Int.MIN_VALUE) return@repeat println(y - b)
		println(minOf(a - x, y - b))
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
