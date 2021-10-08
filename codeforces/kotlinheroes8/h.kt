package codeforces.kotlinheroes8

fun main() {
	val den = 1_000_000.toModular()
	val (n, wid, hei) = readInts()
	val (pSlash, pBackslash) = List(2) { Array(wid + hei + 1) { 1.toModular() } } // prob of being free
	val (vertical, horizontal) = listOf(wid, hei).map { s -> BooleanArray(s + 1).also { it[0] = true; it[s] = true } }
	repeat(n) {
		val (x, y, pIn) = readInts()
		val p = 1 - pIn / den
		vertical[x] = true; horizontal[y] = true
		pBackslash[x + y] *= p; pSlash[x - y + hei] *= p
	}
	val vertices = (wid + 1) * (hei + 1) + wid * hei
	val edgesInitial = vertical.count { it } * hei + horizontal.count { it } * wid
	var ans = (1 - vertices + edgesInitial).toModular()
	for (x in 0..wid) for (y in 0..hei) {
		if (!vertical[x] && !horizontal[y]) ans += pBackslash[x + y] * pSlash[x - y + hei]
		if (x < wid && y < hei) ans += pBackslash[x + y + 1] * pSlash[x - y + hei]
		if (x + 1 <= wid && y - 1 >= 0) ans += 2 * (1 - pBackslash[x + y])
		if (x + 1 <= wid && y + 1 <= hei) ans += 2 * (1 - pSlash[x - y + hei])
	}
	println(ans)
}

fun Int.toModular() = Modular(this)//toDouble()
class Modular {
	companion object {
		const val M = 998244353
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular) = Modular((x + that.x) % M)
	operator fun minus(that: Modular) = Modular((x + M - that.x) % M)
	operator fun times(that: Modular) = (x.toLong() * that.x % M).toInt().toModular()
	fun modInverse() = Modular(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular) = times(that.modInverse())
	override fun toString() = x.toString()
}
operator fun Int.plus(that: Modular) = Modular(this) + that
operator fun Int.minus(that: Modular) = Modular(this) - that
operator fun Int.times(that: Modular) = Modular(this) * that
operator fun Int.div(that: Modular) = Modular(this) / that

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
