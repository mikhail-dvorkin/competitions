package codeforces.codeton6

val toBaseArray = LongArray(Long.SIZE_BITS)

private fun solve(): Long {
	val (n, k) = readLongs()

	val threshold = (n + 1) / k
	fun timesK(m: Long) = if (m <= threshold) m * k else n + 1

	fun toBase(m: Long): Int {
		var i = 0
		var mm = m
		do {
			toBaseArray[i++] = mm % k
			mm /= k
		} while (mm > 0)
		return i
	}
	val nLength = toBase(n)

	fun lex(m: Long): Long {
		val mLength = toBase(m)
		var res = mLength.toLong()
		var prefix = 0L
		var ten = 1L
		for (i in 0 until nLength) {
			prefix = timesK(prefix) + toBaseArray.getOrElse(mLength - 1 - i) { 0 }
			res += prefix - ten
			ten *= k
		}
		return res
	}

	var ans = 0L
	var ten = 1L
	repeat(nLength) {
		val searchHigh = timesK(ten)
		val high = (ten - 1..searchHigh).binarySearch { lex(it) > it }
		val low = (ten - 1..high).binarySearch { lex(it) >= it }
		ans += high - low
		ten = searchHigh
	}
	return ans
}

fun main() = repeat(readInt()) { println(solve()) }

private fun LongRange.binarySearch(predicate: (Long) -> Boolean): Long {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
