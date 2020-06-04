package atcoder.agc044

fun main() = repeat(readInt()) {
	val (n, a, b, c, d) = readLongs()
	val price = longArrayOf(a, b, c)
	val coef = intArrayOf(2, 3, 5)
	val memo = mutableMapOf(0L to 0L)

	fun solve(m: Long): Long {
		memo[m].also { if (it != null) return it }
		var ans = Long.MAX_VALUE
		if (ans / d > m) ans = m * d
		for (i in 0..2) {
			val c = coef[i]
			val p = price[i]
			val r = m % c
			val q = m / c
			ans = minOf(ans, solve(q) + p + r * d)
			if (r == 0L) continue
			if (q + 1 >= m) continue
			ans = minOf(ans, solve(q + 1) + p + (c - r) * d)
		}
		memo[m] = ans
		return ans
	}
	println(solve(n))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
