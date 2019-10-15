fun main() {
	val (_, a, b, k) = readInts()
	val r = readInts().groupBy {
		var x = it
		while (x % k == 0) {
			x /= k
		}
		x
	}
	val ans = r.entries.sumBy { entry ->
		val list = entry.value.groupBy { it }
		val count = IntArray(20) {
			var x = entry.key
			repeat(it) {
				if (x.toLong() * k > Int.MAX_VALUE) x = 0
				x *= k
			}
			list[x]?.size ?: 0
		}
		val count2 = count.clone()
		var res = 0
		for (i in 0..18) {
			val take = minOf(count[i] / a, count[i + 1] / b)
			res += take
			count[i] -= a * take
			count[i + 1] -= b * take
		}
		var res2 = 0
		for (i in 18 downTo 0) {
			val take = minOf(count2[i] / a, count2[i + 1] / b)
			res2 += take
			count2[i] -= a * take
			count2[i + 1] -= b * take
		}
		maxOf(res, res2)
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
