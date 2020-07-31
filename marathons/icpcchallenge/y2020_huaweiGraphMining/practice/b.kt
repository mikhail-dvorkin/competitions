package marathons.icpcchallenge.y2020_huaweiGraphMining.practice

import java.io.*
import kotlin.random.Random

private fun solve(edges: List<List<Int>>): BooleanArray {
	val n = edges.flatten().max()!! + 1
	val nei = List(n) { mutableListOf<Int>() }
	for ((u, v) in edges) { nei[u].add(v); nei[v].add(u) }
	val r = Random(566)
	@Suppress("unused")
	fun greedy() {
		val set = BooleanArray(n)
		val add = IntArray(n) { r.nextInt(n) }
		for (u in nei.indices.sortedBy { nei[it].size * n + add[it] }) {
			set[u] = nei[u].none { set[it] }
		}
	}
	val status = IntArray(n) { 2 }
	val s = 64
	val deg = IntArray(n) { nei[it].size * s + r.nextInt(s) }
	val byDeg = List(deg.max()!! + 1) { mutableSetOf<Int>() }
	for (u in nei.indices) byDeg[deg[u]].add(u)
	while (true) {
		val u = byDeg.firstOrNull { it.isNotEmpty() }?.first() ?: break
		byDeg[deg[u]].remove(u)
		status[u] = 1
		for (v in nei[u]) {
			if (status[v] < 2) continue
			status[v] = 0
			byDeg[deg[v]].remove(v)
			for (w in nei[v]) {
				if (status[w] < 2) continue
				byDeg[deg[w]].remove(w)
				deg[w] -= s
				byDeg[deg[w]].add(w)
			}
		}
	}
	return BooleanArray(n) { status[it] == 1 }
}

fun main() {
	for (test in 1..4) {
		val filename = "b$test"
		val input = BufferedReader(FileReader("$filename.in"))
		val output = PrintWriter("$filename.out")
		fun readLn() = input.readLine()
		fun readStrings() = readLn().split(" ")
		fun readInts() = readStrings().map { it.toInt() }
		val (_, m) = readInts()
		val edges = List(m) { readInts().map { it - 1 } }
		val ans = solve(edges).map { if (it) 1 else 0 }
		output.println(ans.sum())
		output.println(ans.joinToString(" "))
		output.close()
	}
}
