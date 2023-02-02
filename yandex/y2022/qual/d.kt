package yandex.y2022.qual

import kotlin.random.Random

fun main() {
	val (n, _) = readInts()
	val s = List(n) { readLn().map { "ACGT".indexOf(it) }.toIntArray() }
		.let { it.plus(IntArray(it[0].size)) }
	data class Edge(val u: Int, val v: Int, val len: Int)
	val edges = mutableListOf<Edge>()
	for (i in s.indices) for (j in 0 until i) {
		val len = s[i].indices.sumOf { k -> dist(s[i][k], s[j][k]) }
		edges.add(Edge(i, j, len))
	}
	val dsu = DisjointSetUnion(s.size)
	var ans = 0
	for (edge in edges.sortedBy { it.len }) {
		if (dsu.unite(edge.u, edge.v)) ans += edge.len
	}
	println(ans)
}

private fun dist(x: Int, y: Int): Int {
	if (x == y) return 0
	if (x xor y == 2) return 2
	return 1
}

private class DisjointSetUnion(n: Int) {
	var p: IntArray
	var r: Random = Random(566)

	init {
		p = IntArray(n)
		clear()
	}

	fun clear() {
		for (i in p.indices) {
			p[i] = i
		}
	}

	operator fun get(v: Int): Int {
		if (p[v] == v) {
			return v
		}
		p[v] = get(p[v])
		return p[v]
	}

	fun unite(v: Int, u: Int): Boolean {
		val vv = get(v)
		val uu = get(u)
		if (vv == uu) return false
		if (r.nextBoolean()) {
			p[vv] = uu
		} else {
			p[uu] = vv
		}
		return true
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
