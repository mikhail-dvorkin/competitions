package atcoder.arc159

fun main() {
	val (n, _) = readInts()
	val a = List(n) { readInts() }
	val inf = Int.MAX_VALUE / 3
	val b = List(2 * n) { i -> IntArray(2 * n) { j -> a[i % n][j % n].let { if (it == 1) 1 else inf } } }
	for (k in b.indices) for (i in b.indices) for (j in b.indices) {
		b[i][j] = minOf(b[i][j], b[i][k] + b[k][j])
	}
	repeat(readInt()) {
		val (s0, t0) = readLongs().map { it - 1 }
		val s = s0 % n
		val t = t0 % n + (if (s0 / n == t0 / n) 0 else n)
		println(b[s.toInt()][t.toInt()].let { if (it == inf) -1 else it })
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
