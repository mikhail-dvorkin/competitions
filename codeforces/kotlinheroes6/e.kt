package codeforces.kotlinheroes6

private fun solve() {
	readLn()
	val a = readInts()
	val st = SegmentsTreeMax(a.size)
	val seen = IntArray(a.size + 1) { -1 }
	for (i in a.indices) {
		val x = a[i]
		val prev = seen[x]
		if (prev == -1) {
			seen[x] = i
			continue
		}
		val best = maxOf(st.get(prev, i), if (prev + 1 == i) 0 else 1)
		st.set(prev, best + 2)
	}
	println(maxOf(st.get(0, a.size), 1))
}


fun main() = repeat(readInt()) { solve() }

private class SegmentsTreeMax(size: Int) {
	val two: Int
	val a: Array<Int>

	init {
		var t = 1
		while (t < size) {
			t *= 2
		}
		two = t
		a = Array<Int>(2 * two, {_ -> 0})
	}

	fun get(from: Int, to: Int): Int {
		var res = Integer.MIN_VALUE
		var x = two + from
		var y = two + to
		while (x < y) {
			if (x % 2 == 1) {
				res = Math.max(res, a[x])
				x++
			}
			if (y % 2 == 1) {
				y--
				res = Math.max(res, a[y])
			}
			x /= 2
			y /= 2
		}
		return res
	}

	fun set(pos: Int, v: Int) {
		var x = two + pos
		a[x] = v
		while (x >= 2) {
			x /= 2
			a[x] = Math.max(a[2 * x], a[2 * x + 1])
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
