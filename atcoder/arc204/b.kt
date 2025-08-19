package atcoder.arc204

private fun solve() {
	val (n, _) = readInts()
	var ans = 0
	val p = readInts().map { it - 1 }
	val pMark = BooleanArray(p.size)
	for (i in p.indices) {
		if (pMark[i]) continue
		val q = mutableListOf<Int>()
		var j = i
		while (true) {
			q.add(j)
			pMark[j] = true
			j = p[j]
			if (j == i) break
		}
		ans += solve(n, q)
	}
	println(ans)
}

private fun solve(n: Int, q: List<Int>): Int {
	if (q.size == 1) return 0
	val qm = q.map { it % n }
	val a = List(q.size) { IntArray(q.size + 1) }
	val friends = List(q.size) { i ->
		(i + 1 until q.size).filter { qm[it] == qm[i] }
	}
	for (j in 0..q.size) for (i in j - 2 downTo 0) {
		var ans = a[i + 1][j]
		for (k in friends[i]) {
			if (k >= j) break
			ans = maxOf(ans, a[i][k] + a[k][j] + 1)
		}
		a[i][j] = ans
	}
	return a[0][q.size]
}

fun main() = repeat(1) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
