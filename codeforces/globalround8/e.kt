package codeforces.globalround8

private fun solve(out: List<MutableList<Int>>): String {
	val layer = IntArray(out.size)
	for (u in out.indices) {
		if (layer[u] == 2) continue
		for (v in out[u]) layer[v] = maxOf(layer[v], layer[u] + 1)
	}
	val ans = layer.indices.filter { layer[it] == 2 }
	return "${ans.size}\n${ans.map { it + 1 }.joinToString(" ")}\n"
}

fun main() = repeat(readInt()) {
	val (n, m) = readInts()
	val out = List(n) { ArrayList<Int>(2) }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		out[u].add(v)
	}
	for (s in out.indices) out[s].sort()
	output.append(solve(out))
}.also { println(output) }

val output = StringBuilder()
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
