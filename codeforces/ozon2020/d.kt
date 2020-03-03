package codeforces.ozon2020

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val possible = nei.indices.toMutableSet()
	while (possible.size > 1) {
		val uv = possible.filter { nei[it].intersect(possible).size == 1 }.take(2)
		println("? ${uv.map { it + 1 }.joinToString(" ")}")
		val w = readInt() - 1
		if (w in uv) {
			possible.clear(); possible.add(w)
			break
		}
		possible.removeAll(uv)
	}
	println("! ${possible.first() + 1}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
