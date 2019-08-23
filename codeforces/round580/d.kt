package codeforces.round580

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
		nei[b].add(a)
	}
	for (v in nei.indices) {
		val pairs = nei[v].map { Pair(dfsSize(it, v, nei) + 1, it) }.sortedBy { it.first }
		for (i in pairs.indices) {
			val size = pairs.subList(0, i).sumBy { it.first }
			val res = (size + 1) * (n - size) - 1
			if (res < 2 * n * n / 9) continue
			dfsPaint(v, pairs.subList(i, pairs.size).map { it.second }, 1, nei)
			dfsPaint(v, pairs.subList(0, i).map { it.second }, size + 1, nei)
			return
		}
	}
}

private fun dfsSize(v: Int, p: Int, nei: List<List<Int>>): Int = nei[v].minus(p).map { 1 + dfsSize(it, v, nei) }.sum()

private fun dfsPaint(v: Int, p: Collection<Int>, k: Int, nei: List<List<Int>>) {
	var sum = 0
	for (u in nei[v].minus(p)) {
		setValue(v, u, k * (sum + 1))
		dfsPaint(u, setOf(v), k, nei)
		sum += dfsSize(u, v, nei) + 1
	}
}

private fun setValue(u: Int, v: Int, value: Int) = println("${v + 1} ${u + 1} $value")

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
