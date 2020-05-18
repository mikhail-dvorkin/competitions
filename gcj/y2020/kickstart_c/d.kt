package gcj.y2020.kickstart_c

import java.util.*

private fun solve(): Long {
	val (n, q) = readInts()
	val a = readInts().toMutableList()
	val ft = FenwickTree(n)
	for (i in a.indices) {
		ft.add(i, a[i].toLong())
	}
	var ans = 0L
	repeat(q) {
		val (op, xIn, yIn) = readStrings()
		val x = xIn.toInt() - 1; val y = yIn.toInt()
		if (op == "U") {
			ft.add(x, y.toLong() - a[x])
			a[x] = y
		} else {
			ans += ft.query(x, y)
		}
 	}
	return ans
}

class FenwickTree(val n: Int) {
	val sum = LongArray(n)
	val sumCoef = LongArray(n)

	fun add(index: Int, value: Long) {
		var v = value
		if (index % 2 == 1) v *= -1
		var i = index
		while (i < n) {
			sum[i] += v
			sumCoef[i] += v * (index + 1)
			i += i + 1 and -(i + 1)
		}
	}

	fun sum(index: Int): Pair<Long, Long> {
		var i = index - 1
		var res = 0L
		var resCoef = 0L
		while (i >= 0) {
			res += sum[i]
			resCoef += sumCoef[i]
			i -= i + 1 and -(i + 1)
		}
		return res to resCoef
	}

	fun query(start: Int, end: Int): Long {
		val (pref, prefCoef) = sum(start)
		val (suf, sufCoef) = sum(end)
		val r = sufCoef - prefCoef - (suf - pref) * start
		return if (start % 2 == 1) -r else r
	}
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
