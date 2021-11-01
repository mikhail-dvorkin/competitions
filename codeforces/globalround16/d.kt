package codeforces.globalround16

private fun solve() {
	val (hei, wid) = readInts()
	val a = readInts()
	val places = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
	val aSorted = a.sorted()
	for (i in aSorted.indices) {
		val v = aSorted[i]
		if (v !in places) {
			places[v] = mutableListOf()
		}
		places[v]!!.add(i / wid to i % wid)
	}
	for (v in places.keys) {
		places[v]!!.sortBy { - it.first * wid + it.second }
	}
	var ans = 0L
	val f = List(hei) { FenwickTree(wid) }
	for (v in a) {
		val (x, y) = places[v]!!.removeAt(places[v]!!.lastIndex)
		ans += f[x].sum(y)
		f[x].add(y, 1)
	}
	println(ans)
}

class FenwickTree(n: Int) {
	var t: LongArray
	fun add(i: Int, value: Long) {
		var i = i
		while (i < t.size) {
			t[i] += value
			i += i + 1 and -(i + 1)
		}
	}

	fun sum(i: Int): Long {
		var i = i
		var res: Long = 0
		i--
		while (i >= 0) {
			res += t[i]
			i -= i + 1 and -(i + 1)
		}
		return res
	}

	fun sum(start: Int, end: Int): Long {
		return sum(end) - sum(start)
	}

	operator fun get(i: Int): Long {
		return sum(i, i + 1)
	}

	operator fun set(i: Int, value: Long) {
		add(i, value - get(i))
	}

	init {
		t = LongArray(n)
	}
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
