package codeforces.kotlinheroes8

fun main() {
	val (n, m) = readInts()
	data class Attack(val warrior: Int, val barricade: Int, val damage: Int)
	val attacks = mutableListOf<Attack>()
	repeat(n) { warrior ->
		readLn()
		val damages = readInts()
		val barricades = readInts().map { m - it }
		for (i in damages.indices) {
			val a = Attack(warrior, barricades[i], damages[i])
			if (a.barricade > a.warrior) continue
			attacks.add(a)
		}
	}
	val st = SegmentsTreeSimple(n + 1)
	for (a in attacks.sortedBy { it.barricade }) {
		val level = a.warrior - a.barricade
		val best = if (level == 0) 0L else st.getMax(0, level)
		st[level] = maxOf(a.damage + best, st[level])
	}
	println(st.getMax(0, n + 1))
}

class SegmentsTreeSimple(var n: Int) {
	var max: LongArray
	var size = 1

	operator fun set(index: Int, value: Long) {
		var i = size + index
		max[i] = value
		while (i > 1) {
			i /= 2
			max[i] = maxOf(max[2 * i], max[2 * i + 1])
		}
	}

	operator fun get(index: Int): Long {
		return max[size + index]
	}

	fun getMax(from: Int, to: Int): Long {
		var from = from
		var to = to
		from += size
		to += size
		var res = Long.MIN_VALUE
		while (from < to) {
			if (from % 2 == 1) {
				res = maxOf(res, max[from])
				from++
			}
			if (to % 2 == 1) {
				to--
				res = maxOf(res, max[to])
			}
			from /= 2
			to /= 2
		}
		return res
	}

	init {
		while (size <= n) {
			size *= 2
		}
		max = LongArray(2 * size)
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
