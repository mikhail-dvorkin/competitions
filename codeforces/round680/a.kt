package codeforces.round680

private fun solve(): Long {
	val (p, q) = readLongs()
	if (p % q != 0L) return p
	val xs = primeFactorization(q)
	var ans = 1L
	for (i in xs) {
		var pp = p
		while (pp % q == 0L && pp % i == 0L) pp /= i
		if (pp % q != 0L) ans = maxOf(ans, pp)
	}
	return ans
}

fun primeFactorization(n: Long): LongArray {
	var m = n
	val tempLong = mutableListOf<Long>()
	run {
		var i: Long = 2
		while (m > 1 && i * i <= m) {
			while (m % i == 0L) {
				tempLong.add(i)
				m /= i
			}
			i++
		}
	}
	if (m > 1) tempLong.add(m)
	val factors = LongArray(tempLong.size)
	for (i in factors.indices) {
		factors[i] = tempLong[i]
	}
	return factors
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
