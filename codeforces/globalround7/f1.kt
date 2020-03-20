package codeforces.globalround7

fun main() {
	val n = readInt()
	val nei = List(n) { readLn().map { it == '1' } }
	val a = List(n) { List(1 shl n) { mask -> LongArray(1 shl (maxOf(Integer.bitCount(mask), 1) - 1)) } }
	for (mask in a.last().indices) {
		for (last in 0 until n) {
			if (((mask shr last) and 1) == 0) continue
			val m = Integer.bitCount(mask)
			if (m == 1) {
				a[last][mask][0] = 1
				continue
			}
			val withoutLast = mask xor (1 shl last)
			for (i in 0 until n) {
				if (((mask shr i) and 1) == 0 || i == last) continue
				val bit = if (nei[last][i]) 1 else 0
				for (m2 in 0 until (1 shl (m - 2))) {
					val m3 = m2 * 2 + bit
					a[last][mask][m3] += a[i][withoutLast][m2]
				}
			}
		}
	}
	val ans = LongArray(1 shl (n - 1))
	for (last in 0 until n) {
		for (mask in ans.indices) {
			ans[mask] += a[last].last()[mask]
		}
	}
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
