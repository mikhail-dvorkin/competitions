package codeforces.kotlinheroes6.practice

fun main() {
	val (_, m) = readInts()
	val a = readLongs()
	val capital = a.withIndex().minByOrNull { it.value }!!.index
	data class Edge(val u: Int, val v: Int, val weight: Long)
	val edges = List(m) {
		val (uIn, vIn, weight) = readLongs()
		Edge((uIn - 1).toInt(), (vIn - 1).toInt(), weight)
	} + List(a.size) {
		Edge(it, capital, a[it] + a[capital])
	}
	val dsu = DisjointSetUnion(a.size)
	var ans = 0L
	for (edge in edges.sortedBy { it.weight }) {
		if (dsu[edge.u] == dsu[edge.v]) continue
		dsu.unite(edge.u, edge.v)
		ans += edge.weight
	}
	println(ans)
}

class DisjointSetUnion(n: Int) {
	val p: IntArray = IntArray(n) { it }
	val r = java.util.Random(566)
	operator fun get(v: Int): Int = if (p[v] == v) v else { p[v] = get(p[v]); p[v] }
	fun unite(v: Int, u: Int) = if (r.nextBoolean()) p[get(v)] = get(u) else p[get(u)] = get(v)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
