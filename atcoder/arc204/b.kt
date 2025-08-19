package atcoder.arc204

private fun solve() {
	val (mod, _) = readInts()
	val p = readInts().map { it - 1 }
	val mark = BooleanArray(p.size)
	var ans = 0
	for (i in p.indices) if (!mark[i]) {
		val q = generateSequence(i) { j -> p[j].takeIf { it != i } }.toList()
		for (j in q) mark[j] = true
		ans += solve(mod, q)
	}
	println(ans)
}

private fun solve(mod: Int, q: List<Int>): Int {
	val friends = List(q.size) { i ->
		(i + 1 until q.size).filter { (q[it] - q[i]) % mod == 0 }
	}
	val a = List(q.size) { IntArray(q.size + 1) }
	for (j in 0..q.size) for (i in j - 2 downTo 0) {
		a[i][j] = a[i + 1][j]
		for (k in friends[i]) {
			if (k >= j) break
			a[i][j] = maxOf(a[i][j], a[i][k] + a[k][j] + 1)
		}
	}
	return a[0][q.size]
}

fun main() = repeat(1) { solve() }

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
