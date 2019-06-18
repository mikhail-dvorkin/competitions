package facebook.y2019.qual

import kotlin.random.Random

private fun solve(): String {
	val (n, m) = readInts()
	val requirements = List(m) { readInts().map { it - 1 } }
	val ans = IntArray(n)
	try {
		construct(ans, -1, List(n) { it }, requirements)
	} catch (_: IllegalArgumentException) {
		return "Impossible"
	}
	return ans.map { it + 1 }.joinToString(" ")
}

private fun construct(ans: IntArray, parent: Int, vertices: Collection<Int>, requirements: List<List<Int>>) {
	val possibleRoots = vertices.toMutableSet()
	for (r in requirements) {
		repeat(2) {
			if (r[it] != r[2]) {
				possibleRoots.remove(r[it])
			}
		}
	}
	fun canBeRoot(root: Int): Pair<Int, DisjointSetUnion>? {
		val different = mutableListOf<Pair<Int, Int>>()
		val dsu = DisjointSetUnion(ans.size)
		for (r in requirements) {
			if (r[2] == root) {
				if ((r[0] != root) and (r[1] != root)) {
					different.add(Pair(r[0], r[1]))
				}
				continue
			}
			dsu.unite(r[0], r[1])
			dsu.unite(r[0], r[2])
		}
		for ((a, b) in different) {
			if (dsu[a] == dsu[b]) {
				return null
			}
		}
		return Pair(root, dsu)
	}
	val (root, dsu) = possibleRoots.mapNotNull(::canBeRoot).firstOrNull() ?: throw IllegalArgumentException()
	ans[root] = parent
	val children = vertices.filter { (dsu[it] == it) and (it != root) }
	val requirementsByChild = requirements.filter { it[2] != root }.groupBy { dsu[it[0]] }
	val verticesByChild = vertices.minus(root).groupBy { dsu[it] }
	for (v in children) {
		construct(ans, root, verticesByChild.getValue(v), requirementsByChild.getOrElse(v) { listOf() })
	}
}

class DisjointSetUnion(n: Int) {
	internal val p: IntArray = IntArray(n) { it }
	internal val r = Random(566)

	internal operator fun get(v: Int): Int {
		if (p[v] == v) {
			return v
		}
		p[v] = get(p[v])
		return p[v]
	}

	internal fun unite(v: Int, u: Int) {
		val a = get(v)
		val b = get(u)
		if (r.nextBoolean()) {
			p[a] = b
		} else {
			p[b] = a
		}
	}
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
