package gcj.y2020.qual

private fun solve(possible: String = "POSSIBLE", impossible: String = "IMPOSSIBLE"): String {
//	for (n in 2..50) { solve(n, 0, 0); solve(n, 1, 1); solve(n, 1, 2) }
	val (n, trace) = readInts()
	for (i in 1..n) for (j in 1..n) for (k in 1..n) {
		if ((i == j) xor (i == k) || (n - 2) * i + j + k != trace) continue
		val d0 = setOf(i, j).size - 1
		val d1 = setOf(i, j, k).size - 1
		val a = solveDiagonal(n, d0, d1) ?: continue
		val switch = IntArray(n) { -1 }
		switch[0] = i
		switch[d0] = j
		switch[d1] = k
		for (x in a.indices) if (switch[x] == -1) switch[x] = (1..n).first { it !in switch }
		return possible + "\n" + a.joinToString("\n") { row -> row.joinToString(" ") { switch[it].toString() } }
	}
	return impossible
}

private val memo = mutableMapOf<String, List<IntArray>?>()

private fun solveDiagonal(n: Int, d0: Int, d1: Int): List<IntArray>? {
	val code = "$n $d0 $d1"
	if (code in memo) return memo[code]
	val a = List(n) { IntArray(n) { -1 } }
	a.indices.forEach { i -> a[i][i] = 0 }
	a[0][0] = d0
	a[1][1] = d1
	memo[code] = if (d1 >= n) null else search(a)
	return memo[code]
}

private fun search(a: List<IntArray>, magic: Int = 3): List<IntArray>? {
	val n = a.size
	for (x in magic until n) for (y in magic until n) a[x][y] = (y - x + n) % n
	for (x in magic until n - magic) {
		a[0][x] = x - 1
		a[x][0] = n + 1 - x
		a[x][1] = n + 2 - x
		a[x][2] = n - x
	}
	val p = List(n) { LongArray(n) { -1L } }
	while (true) {
		for (i in a.indices) for (j in a.indices) {
			val v = a[i][j]
			if (v == -1) continue
			for (x in a.indices) {
				if (x != i) p[x][j] = p[x][j] and (1L shl v).inv()
				if (x != j) p[i][x] = p[i][x] and (1L shl v).inv()
				if (x != v) p[i][j] = p[i][j] and (1L shl x).inv()
			}
		}

		var improved = false
		for (x in a.indices) {
			for (y in a.indices) {
				var f = a.indices.filter { ((p[x][it] shr y) and 1) > 0 }
				if (f.isEmpty()) return null
				if (f.size == 1 && a[x][f.first()] == -1) {
					a[x][f.first()] = y
					improved = true
				}
				f = a.indices.filter { ((p[it][x] shr y) and 1) > 0 }
				if (f.isEmpty()) return null
				if (f.size == 1 && a[f.first()][x] == -1) {
					a[f.first()][x] = y
					improved = true
				}
				f = a.indices.filter { ((p[x][y] shr it) and 1) > 0 }
				if (f.isEmpty()) return null
				if (f.size == 1 && a[x][y] == -1) {
					a[x][y] = f.first()
					improved = true
				}
			}
		}
		if (!improved) break
	}

	for (i in a.indices) for (j in a.indices) {
		if (a[i][j] >= 0) continue
		for (v in a.indices) {
			if (((p[i][j] shr v) and 1) == 0L) continue
			val b = a.map { it.clone() }
			b[i][j] = v
			val res = search(b)
			if (res != null) return res
		}
		return null
	}
	return a
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
