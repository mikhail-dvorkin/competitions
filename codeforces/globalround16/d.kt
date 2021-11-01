package codeforces.globalround16

private fun solve() {
	val (hei, wid) = readInts()
	val a = readInts()
	val places = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
	val aSorted = a.sorted()
	for (i in aSorted.indices) {
		places.getOrPut(aSorted[i]) { mutableListOf() }.add(i / wid to i % wid)
	}
	places.forEach { (_, list) -> list.sortBy { - it.first * wid + it.second } }
	val f = List(hei) { FenwickTree(wid) }
	val ans = a.sumOf { v ->
		val (x, y) = places[v]!!.removeLast()
		f[x].sum(y).also { f[x].add(y, 1) }
	}
	println(ans)
}

class FenwickTree(n: Int) {
	var t: LongArray
	fun add(index: Int, value: Long) {
		var i = index
		while (i < t.size) {
			t[i] += value
			i += i + 1 and -(i + 1)
		}
	}

	fun sum(index: Int): Long {
		var i = index
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
