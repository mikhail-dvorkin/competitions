package codeforces.kotlinheroes4

fun main() {
	val (hei, wid, q) = readInts()
	val a = List(hei) { readLongs().toLongArray() }
	val rows = LongArray(hei) { a[it].sum() }
	val cols = LongArray(wid) { a.map { row -> row[it] }.sum() }
	val ans = List(q + 1) { iter ->
		if (iter > 0) {
			val (xIn, yIn, z) = readInts()
			val x = xIn - 1; val y = yIn - 1
			val prev = a[x][y]
			rows[x] += z - prev
			cols[y] += z - prev
			a[x][y] = z.toLong()
		}
		solve(rows) + solve(cols)
	}
	println(ans.joinToString(" "))
}

private fun solve(a: LongArray): Long {
	val sum = a.sum()
	var cur = a.mapIndexed { index, x -> x * index }.sum()
	var ans = cur
	var pref = a[0]
	for (i in 1 until a.size) {
		cur += pref - (sum - pref)
		pref += a[i]
		ans = minOf(ans, cur)
	}
	return ans
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
