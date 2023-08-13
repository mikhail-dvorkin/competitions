package gcj.y2020.kickstart_d

private fun solve(): String {
	val (n, q) = readInts()
	val d = readInts().toIntArray()
	val maxD = d.maxOrNull()!!
	val st = SparseTable(d)

	fun canWalk(s: Int, t: Int): Pair<Int, Int> {
		var low = s
		var high = n
		while (low + 1 < high) {
			val mid = (low + high) / 2
			val maxDoor = st.max(s, mid)
			if (maxDoor <= t) low = mid else high = mid
		}
		val ans1 = low - s
		low = s
		high = -1
		while (low - 1 > high) {
			val mid = (low + high) / 2
			val maxDoor = st.max(mid, s)
			if (maxDoor <= t) low = mid else high = mid
		}
		val ans2 = s - low
		return ans1 to ans2
	}

	val ans = IntArray(q) {
		val (sIn, k) = readInts()
		val s = sIn -1
		if (k == 1) return@IntArray s
		var low = 0
		var high = maxD
		while (low + 1 < high) {
			val mid = (low + high) / 2
			val cw = canWalk(s, mid)
			if (cw.first + cw.second + 1 >= k) high = mid else low = mid
		}
		val cw = canWalk(s, high)
		val cwp = canWalk(s, high - 1)
		if (cw.first != cwp.first) s - cw.first else s + cw.second
		high
	}
	return ans.joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

class SparseTable(a: IntArray) {
	var min: Array<IntArray>
	var max: Array<IntArray>
	fun min(from: Int, to: Int): Int {
		val j = Integer.SIZE - 1 - Integer.numberOfLeadingZeros(to - from)
		return Math.min(min[j][from], min[j][to - (1 shl j)])
	}

	fun max(from: Int, to: Int): Int {
		val j = Integer.SIZE - 1 - Integer.numberOfLeadingZeros(to - from)
		return Math.max(max[j][from], max[j][to - (1 shl j)])
	}

	init {
		val n = a.size
		var t = 1
		while (1 shl t <= n) {
			t++
		}
		min = Array(t) { IntArray(n) }
		max = Array(t) { IntArray(n) }
		System.arraycopy(a, 0, min[0], 0, n)
		System.arraycopy(a, 0, max[0], 0, n)
		for (j in 1 until t) {
			var i = 0
			while (i + (1 shl j) <= n) {
				min[j][i] = Math.min(min[j - 1][i], min[j - 1][i + (1 shl j - 1)])
				max[j][i] = Math.max(max[j - 1][i], max[j - 1][i + (1 shl j - 1)])
				i++
			}
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
