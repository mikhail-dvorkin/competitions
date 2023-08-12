package yandex.y2022.finals

fun main() {
	val (a, m) = readLongs()
	val ps = mutableListOf<Long>()
	var b = a
	for (p in 2..b) {
		if (b == 1L) break
		if (p * p > b) {
			ps.add(b)
			break
		}
		if (b % p != 0L) continue
		ps.add(p)
		while (b % p == 0L) b /= p
	}
	var ans = Long.MAX_VALUE
	for (r in 0..2) {
		if ((r != 0) and (3 in ps)) continue
		var cur = 1L
		for (p in ps) {
			b = a
			var k = 0L
			while (b % p == 0L) {
				b /= p
				k++
			}
			k *= m
			if (p == 3L) {
				cur = maxOf(cur, searchUsual(k, 3))
				continue
			}
			if (r == 0) {
				cur = maxOf(cur, 3 * searchUsual(k, p))
				continue
			}
			cur = maxOf(cur, searchUnusual(k, p, r))
		}
		ans = minOf(ans, cur)
	}
	println(ans)
}

fun searchUsual(m: Long, p: Long): Long {
	var low = 0L
	var high = p * m
	while (low + 1 < high) {
		val mid = low + (high - low) / 2
		var x = mid / p
		var count = 0L
		while (x > 0) {
			count += x
			x /= p
		}
		if (count >= m) high = mid else low = mid
	}
	return high
}

fun searchUnusual(m: Long, p: Long, r: Int): Long {
	var low = 0L
	var high = m * p
	while (low + 1 < high) {
		val mid = low + (high - low) / 2
		var t = p
		var count = 0L
		while (t <= 3 * mid + r) {
			val f = (if ((t % 3).toInt() == r) t else 2 * t) / 3
			if (mid >= f) {
				count += (mid - f) / t + 1
			}
//			if (t * 3 > 3 * mid + r) break
			if (t >= mid + 1) break
			t *= p
		}
		if (count >= m) high = mid else low = mid
	}
	return 3 * high + r
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
