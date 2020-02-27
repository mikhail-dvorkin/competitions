package codeforces.kotlinheroes3

private val digits = 1..9

fun main() {
	val (m, kIn) = readInts()
	listOf(2, 3, 5, 7).fold(m) { acc, p ->
		var temp = acc
		while (temp % p == 0) temp /= p
		temp
	}.takeIf { it == 1 } ?: return println(-1)

	val memo = mutableMapOf<Pair<Int, Int>, Long>()
	fun count(m: Int, len: Int): Long {
		if (len == 0) return if (m == 1) 1 else 0
		val pair = m to len
		memo[pair]?.let { return it }
		val res = digits.filter { m % it == 0 }.map { count(m / it, len - 1) }.sum()
		memo[pair] = res
		return res
	}

	var k = kIn - 1L
	val len = (1..m + kIn).first { len ->
		val here = count(m, len)
		if (k >= here) {
			k -= here
			false
		} else true
	}

	var mm = m
	val ans = List(len) { i ->
		for (d in digits.filter { mm % it == 0 }) {
			val here = count(mm / d, len - 1 - i)
			if (k >= here) {
				k -= here
				continue
			}
			mm /= d
			return@List d
		}
	}
	return println(ans.joinToString(""))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
