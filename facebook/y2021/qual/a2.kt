package facebook.y2021.qual

private fun solve(from: Char = 'A', toInclusive: Char = 'Z'): Int {
	val s = readLn()
	val letters = toInclusive - from + 1
	val inf = letters + 1
	val e = List(letters) { IntArray(letters) { inf } }
	repeat(readInt()) {
		val (u, v) = readLn().map { it - from }
		e[u][v] = 1
	}
	for (k in e.indices) {
		e[k][k] = 0
		for (i in e.indices) {
			for (j in e.indices) {
				e[i][j] = minOf(e[i][j], e[i][k] + e[k][j])
			}
		}
	}
	fun cost(k: Int): Int? {
		return s.sumOf { c -> e[c - from][k].also { if (it == inf) return null } }
	}
	return e.indices.mapNotNull { cost(it) }.minOrNull() ?: -1
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
