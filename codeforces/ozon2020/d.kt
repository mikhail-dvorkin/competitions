package codeforces.ozon2020

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	val poss = MutableList(n) { true }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	while (poss.count { it } != 1) {
		val nice = nei.indices.filter { u ->
			poss[u] && (nei[u].count { poss[it] } == 1)
		}
		val (u, v) = nice.take(2)
		println("? ${u + 1} ${v + 1}")
		System.out.flush()
		val w = readInt() - 1
		if (w == u || w == v) {
			poss.fill(false)
			poss[w] = true
			break
		}
		poss[u] = false
		poss[v] = false
	}
	println("! ${poss.indexOf(true) + 1}")
	System.out.flush()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
