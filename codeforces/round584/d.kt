package codeforces.round584

fun main() {
	val (n, k) = readInts()
	val dsu = DisjointSetUnion(n)
	repeat(k) {
		val (a, b) = readInts().map { it - 1 }
		dsu.unite(a, b)
	}
	println(k - (0 until n).groupBy { dsu[it] }.values.sumBy { it.size - 1 })
}

class DisjointSetUnion(n: Int) {
	internal val p: IntArray = IntArray(n) { it }
	internal val r = java.util.Random(566)
	internal operator fun get(v: Int): Int = if (p[v] == v) v else { p[v] = get(p[v]); p[v] }
	internal fun unite(v: Int, u: Int) = if (r.nextBoolean()) p[get(v)] = get(u) else p[get(u)] = get(v)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
