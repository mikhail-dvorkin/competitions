package codeforces.globalround5

fun main() {
	val n = readInt()
	val a = readInts()
	val b = a.plus(a).plus(a)
	val st = SegmentsTreeSimple(b.size)
	for (i in b.indices) {
		st.set(i, b[i])
	}
	val r = IntArray(b.size)
	val ans = IntArray(b.size)
	for (i in b.indices.reversed()) {
		val v = b[i]
		var low = i + 1
		var high = b.size + 1
		while (low + 1 < high) {
			val mid = (low + high) / 2
			if (st.getMin(i + 1, mid) * 2 < v) {
				high = mid
			} else {
				low = mid
			}
		}
		r[i] = high - 1
		if (i + 1 < b.size) {
			r[i] = minOf(r[i], r[i + 1])
		}
		ans[i] = r[i] - i
		if (r[i] == b.size) ans[i] = -1
	}
	print(ans.take(n).joinToString(" "))
}

class SegmentsTreeSimple(internal var n: Int) {
	internal var min: IntArray
	internal var size: Int = 0

	init {
		size = 1
		while (size <= n) {
			size *= 2
		}
		min = IntArray(2 * size)
	}

	internal operator fun set(index: Int, value: Int) {
		var i = size + index
		min[i] = value
		while (i > 1) {
			i /= 2
			min[i] = Math.min(min[2 * i], min[2 * i + 1])
		}
	}

	internal operator fun get(index: Int): Int {
		return min[size + index]
	}

	internal fun getMin(from: Int, to: Int): Int {
		var from = from
		var to = to
		from += size
		to += size
		var res = Integer.MAX_VALUE
		while (from < to) {
			if (from % 2 == 1) {
				res = Math.min(res, min[from])
				from++
			}
			if (to % 2 == 1) {
				to--
				res = Math.min(res, min[to])
			}
			from /= 2
			to /= 2
		}
		return res
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
