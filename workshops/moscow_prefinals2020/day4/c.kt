package workshops.moscow_prefinals2020.day4

fun main() {
	val s = readLine()!!
	val initMask = s.reversed().replace("(", "0").replace(")", "1").toLong(2)
	val memo = List(s.length + 1) { mutableMapOf<Long, Double>() }
	memo[0][0] = 1.0
	fun solve(n: Int, mask: Long): Double {
		memo[n][mask]?.also { return it }
		if ((mask and 1) != 0L || (mask shr (n - 1)) == 0L) return 0.0
		var options = 0
		var sum = 0.0
		for (j in 0 until n) if (((mask shr j) and 1L) != 0L) {
			for (i in 0 until j) if (((mask shr i) and 1L) == 0L) {
				sum += solve(n - 2, (if (j + 1 < n) (mask shr (j + 1) shl (j - 1)) else 0) +
						(if (i + 1 < j) ((mask and ((1L shl j) - 1)) shr (i + 1) shl i) else 0) +
						(if (0 < i) (mask and ((1L shl i) - 1)) else 0))
				options++
			}
		}
		val res = if (options == 0) 0.0 else sum / options
		memo[n][mask] = res
		return res
	}
	println(solve(s.length, initMask))
}
