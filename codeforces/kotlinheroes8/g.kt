package codeforces.kotlinheroes8

fun main() {
	val (n, m) = readInts()
	data class Attack(val warrior: Int, val barricade: Int, val damage: Int)
	val attacks = List(n) { warrior ->
		val (_, damages, barricadesLeft) = List(3) { readInts() }
		List(damages.size) { Attack(warrior, m - barricadesLeft[it], damages[it]) }
	}.flatten()
	val st = SegmentsTreeSimple(n)
	for (a in attacks.sortedBy { it.barricade }) {
		val level = a.warrior - a.barricade
		if (level < 0) continue
		st[level] = maxOf(st[level], a.damage + maxOf(st.getMax(0, level), 0))
	}
	println(st.getMax(0, n))
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
		var fromVar = from
		var toVar = to
		fromVar += size
		toVar += size
		var res = Long.MIN_VALUE
		while (fromVar < toVar) {
			if (fromVar % 2 == 1) {
				res = maxOf(res, max[fromVar])
				fromVar++
			}
			if (toVar % 2 == 1) {
				toVar--
				res = maxOf(res, max[toVar])
			}
			fromVar /= 2
			toVar /= 2
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
