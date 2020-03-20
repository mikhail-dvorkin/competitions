package codeforces.globalround7

import java.util.*

fun main() {
	val n = readInt()
	val p = readInts().map { it - 1 }
	val q = readInts().map { it - 1 }
	val indexInP = IntArray(n)
	for (i in p.indices) {
		indexInP[p[i]] = i
	}

	val st = SegmentsTreeSimple(2 * n + 2)
	val map = TreeMap<Int, Int>()
	fun addNumber(x: Int) {
		st.set(2 * n + 1 - 2 * x, -1)
	}
	fun addBomb(x: Int) {
		st.set(2 * n + 1 - 2 * x - 1, 1)
	}
	fun alive(): Int {
		return if (st.getMinBalance() < 0) 1 else 0
//		var b = 0
//		for (d in map.values) {
//			b += d
//			if (b < 0) return 1
//		}
//		return 0
	}

	var alive = n - 1
	addNumber(indexInP[alive])
	val ans = mutableListOf(alive)
	for (bomb in 0 until n - 1) {
		addBomb(q[bomb])
		while (alive() == 0) {
			alive--
			addNumber(indexInP[alive])
		}
		ans.add(alive)
	}
	println(ans.map{ it + 1 }.joinToString(" "))
}

class SegmentsTreeSimple(var n: Int) {
	var min: IntArray
	var sum: IntArray
	var size = 1

	operator fun set(index: Int, value: Int) {
		var i = size + index
		sum[i] = value
		min[i] = if (value > 0) 0 else -1
		while (i > 1) {
			i /= 2
			min[i] = Math.min(min[2 * i], sum[2 * i] + min[2 * i + 1])
			sum[i] = sum[2 * i] + sum[2 * i + 1]
		}
	}

	operator fun get(index: Int): Int {
		return min[size + index]
	}

	fun getMinBalance(): Int {
		return min[1]
	}

//	fun getMax(from: Int, to: Int): Int {
//		var from = from
//		var to = to
//		from += size
//		to += size
//		var res = Int.MIN_VALUE
//		while (from < to) {
//			if (from % 2 == 1) {
//				res = Math.max(res, max[from])
//				from++
//			}
//			if (to % 2 == 1) {
//				to--
//				res = Math.max(res, max[to])
//			}
//			from /= 2
//			to /= 2
//		}
//		return res
//	}
//
//	fun getMin(from: Int, to: Int): Int {
//		var from = from
//		var to = to
//		from += size
//		to += size
//		var res = Int.MAX_VALUE
//		while (from < to) {
//			if (from % 2 == 1) {
//				res = Math.min(res, min[from])
//				from++
//			}
//			if (to % 2 == 1) {
//				to--
//				res = Math.min(res, min[to])
//			}
//			from /= 2
//			to /= 2
//		}
//		return res
//	}

	init {
		while (size <= n) {
			size *= 2
		}
		min = IntArray(2 * size)
		sum = IntArray(2 * size)
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
