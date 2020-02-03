package codeforces.globalround5

fun main() {
	val n = readInt()
	val a = readInts()
	val b = a.plus(a).plus(a)
	val st = SegmentsTreeSimple(b)
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

class SegmentsTreeSimple(data: List<Int>) {
	internal val min: IntArray
	internal var size: Int = 1

	init {
		while (size <= data.size) size *= 2
		min = IntArray(2 * size)
		System.arraycopy(data.toIntArray(), 0, min, size, data.size)
		for (i in size - 1 downTo 1) {
			min[i] = minOf(min[2 * i], min[2 * i + 1])
		}
	}

	internal fun getMin(from: Int, to: Int): Int {
		var f = from + size
		var t = to + size
		var res = Integer.MAX_VALUE
		while (f < t) {
			if (f % 2 == 1) {
				res = minOf(res, min[f])
				f++
			}
			if (t % 2 == 1) {
				t--
				res = minOf(res, min[t])
			}
			f /= 2
			t /= 2
		}
		return res
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
