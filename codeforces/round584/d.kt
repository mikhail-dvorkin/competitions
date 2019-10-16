package codeforces.round584

fun main() {
	val (n, k) = readInts()
	val dsu = DisjointSetUnion(n)
	repeat(k) {
		val (a, b) = readInts().map { it - 1 }
		dsu.unite(a, b)
	}
	val happy = (0 until n).groupBy { dsu.get(it) }.values.sumBy { it.size - 1 }
	println(k - happy)
}

class DisjointSetUnion(n: Int) {
	internal var p: IntArray
	internal var r = java.util.Random(566)

	init {
		p = IntArray(n)
		clear()
	}

	internal fun clear() {
		for (i in p.indices) {
			p[i] = i
		}
	}

	internal operator fun get(v: Int): Int {
		if (p[v] == v) {
			return v
		}
		p[v] = get(p[v])
		return p[v]
	}

	internal fun unite(v: Int, u: Int) {
		var v = v
		var u = u
		v = get(v)
		u = get(u)
		if (r.nextBoolean()) {
			p[v] = u
		} else {
			p[u] = v
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
