package yandex.y2022.qual

import kotlin.random.Random

fun main() {
	val (n, q) = readInts()
	val dsu = DisjointSetUnionRPS(n)
	val three = IntArray(n + 1)
	three[0] = 1
	for (i in 1 until three.size) three[i] = (3 * three[i - 1].toModular()).x
	repeat(q) {
		val (uIn, vIn, resultIn) = readStrings()
		val u = uIn.toInt() - 1
		val v = vIn.toInt() - 1
		val result = "LDW".indexOf(resultIn) - 1
		dsu.unite(u, v, result)
		println(if (dsu.broken) 0 else three[dsu.degrees])
	}
}

private class DisjointSetUnionRPS(n: Int) {
	val p: IntArray
	val diffP: IntArray
	val r: Random = Random(566)
	var broken: Boolean
	var degrees: Int

	init {
		p = IntArray(n)
		for (i in p.indices) {
			p[i] = i
		}
		diffP = IntArray(n)
		broken = false
		degrees = n
	}

	operator fun get(v: Int): Pair<Int, Int> {
		if (p[v] == v) return v to 0
		val (r, diffR) = get(p[v])
		p[v] = r
		diffP[v] += diffR
		return p[v] to diffP[v]
	}

	fun unite(u: Int, v: Int, d: Int) {
		val (uu, diffU) = get(u)
		val (vv, diffV) = get(v)
		if (vv == uu) {
			if ((diffU - diffV - d) % 3 != 0) broken = true
			return
		}
		degrees--
		if (r.nextBoolean()) {
			p[vv] = uu
			diffP[vv] = diffU - diffV - d
		} else {
			p[uu] = vv
			diffP[uu] = diffV - diffU + d
		}
	}
}

private fun Int.toModular() = Modular(this)//toDouble()
private class Modular {
	companion object {
		const val M = 1_000_000_007
	}
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

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
