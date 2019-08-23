package codeforces.round580

fun main() {
	val n = readInt()
	val a = readLongs()
	val perBit = List(Long.SIZE_BITS) { mutableListOf<Long>() }
	for (x in a) {
		for (i in perBit.indices) {
			if ((x shr i) and 1 == 0L) continue
			perBit[i].add(x)
		}
	}
	if (perBit.map { it.size }.max()!! >= 3) {
		println(3)
		return
	}
	val v = perBit.flatten().toSet().sorted()
	val inf = v.size + 1
	val edges = List(v.size) { IntArray(v.size) { inf } }
	for (group in perBit) {
		if (group.size != 2) continue
		val (a, b) = group.map { v.indexOf(it) }
		edges[a][b] = 1
		edges[b][a] = 1
	}
	var ans = inf
	for (a in v.indices) {
		for (b in v.indices) {
			if (edges[a][b] != 1) continue
			val e = List(v.size) { edges[it].clone() }
			e[a][b] = inf
			e[b][a] = inf
			for (k in v.indices) {
				for (i in v.indices) {
					for (j in v.indices) {
						e[i][j] = minOf(e[i][j], e[i][k] + e[k][j])
					}
				}
			}
			ans = minOf(ans, e[a][b] + 1)
		}
	}
	println(if (ans < inf) ans else -1)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
