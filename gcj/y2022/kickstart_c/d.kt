package gcj.y2022.kickstart_c

const val M = 1000000007

private fun solve(): Int {
	val n = readInt()
	val s = readLn()
	val a = List(n + 1) { List(n + 1) { IntArray(n + 1) } }
	for (i in n downTo 0) for (j in i..n) for (k in 0..j - i) {
		if (k == 0) {
			a[i][j][k] = 1
			continue
		}
		if (k == 1) {
			a[i][j][k] = j - i
			continue
		}
		a[i][j][k] = (a[i][j - 1][k].toModular() + a[i + 1][j][k].toModular() - a[i + 1][j - 1][k].toModular()).x
		if (s[i] == s[j - 1]) {
			a[i][j][k] = (a[i][j][k].toModular() + a[i + 1][j - 1][k - 2].toModular()).x
		}
	}
	val cnk = List(n + 1) { IntArray(n + 1) }
	for (i in cnk.indices) {
		cnk[i][0] = 1
		cnk[i][i] = 1
		for (j in 1 until i) cnk[i][j] = (cnk[i - 1][j - 1].toModular() + cnk[i - 1][j].toModular()).x
	}
	var ans = 0.toModular()
	for (i in 0 until n) {
		ans += a[0][n][i].toModular().div(cnk[n][i].toModular())
	}
	return ans.x
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()

private fun Int.toModular() = Modular(this)//toDouble()
private class Modular {
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular) = Modular((x + that.x) % M)
	operator fun minus(that: Modular) = Modular((x + M - that.x) % M)
	operator fun times(that: Modular) = (x.toLong() * that.x % M).toInt().toModular()
	private fun modInverse() = Modular(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular) = times(that.modInverse())
	override fun toString() = x.toString()
}
private operator fun Int.plus(that: Modular) = Modular(this) + that
private operator fun Int.minus(that: Modular) = Modular(this) - that
private operator fun Int.times(that: Modular) = Modular(this) * that
private operator fun Int.div(that: Modular) = Modular(this) / that
