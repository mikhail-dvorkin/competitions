package atcoder.agc043

fun main() {
//	val (n, m) = readInts()
	val memo = HashMap<Long, Int>()

	fun solve(x: Int, y: Int, z: Int): Int {
		if (z == 0 && y == x - 1) return 1
		if (y < 0 || y >= x || z >= x) return 0
		val code = (x.toLong() shl 42) + (y.toLong() shl 21) + z
		if (memo.containsKey(code)) return memo[code]!!
		var res = 0
		for (xx in 0 until x) {
			res += solve(xx, y - (x - xx - 1), z)
		}
		if (z > 0) {
			if (z == 1 && x - 1 - y <= 2) {
				res += c(x - 1, x - 1 - y)
			}
			for (xx in 0 until x) {
				for (r1 in 0..2) {
					if (r1 > x - xx - 1) break
					for (r2 in 0..2 - r1) {
						if (r2 >= xx) break
						if (r1 + r2 == 0) continue
						res += solve(xx, y - (x - xx - 1 - r1) + r2, z - 1) * c(x - xx - 1, r1) * c(xx - 1, r2)
					}
				}
			}
		}
		memo[code] = res
		return res
	}

	fun solve(n: Int) = (0..n).sumBy { solve(3 * n, 0, it) }

	println(solve(1))
	println(solve(2))
}

private fun c(n: Int, k: Int): Int {
	return when (k) {
		0 -> 1
		1 -> n
		2 -> n * (n - 1) / 2
		else -> error("")
	}
}
