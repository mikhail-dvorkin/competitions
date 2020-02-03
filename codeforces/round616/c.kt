package codeforces.round616

import kotlin.random.Random

fun solve(k: Int, toSwitch: List<Int>, where: List<List<Int>>): List<Int> {
	val p = MutableList(k) { it }
	val odd = MutableList(k) { 0 }
	val d0 = MutableList(k) { 0 }
	val d1 = MutableList(k) { 1 }
	val ans = mutableListOf(0)
	fun get(x: Int): Int {
		val ps = mutableListOf(x)
		while (p[ps.last()] != ps.last()) {
			ps.add(p[ps.last()])
		}
		for (i in ps.size - 2 downTo 0) {
			p[ps[i]] = ps.last()
			odd[ps[i]] = odd[ps[i]] xor odd[ps[i + 1]]
		}
		return p[x]
//		if (p[x] == x) return x
//		val par = p[x]
//		p[x] = get(par)
//		odd[x] = odd[x] xor odd[par]
//		return p[x]
	}
	val rnd = Random(566)
	for (x in toSwitch.indices) {
		if (where[x].isEmpty()) {
			ans.add(ans.last())
			continue
		}
		if (where[x].size == 1) {
			val (c) = where[x]
			val cc = get(c)
			val xor = odd[c] xor toSwitch[x]
			val old = minOf(d0[cc], d1[cc])
			if (xor == 1) {
				d0[cc] = k + 1
			} else {
				d1[cc] = k + 1
			}
			val new = minOf(d0[cc], d1[cc])
			ans.add(ans.last() - old + new)
			continue
		}
		val (c, d) = where[x].shuffled(rnd)
		val cc = get(c)
		val dd = get(d)
		if (cc == dd) {
			ans.add(ans.last())
			continue
		}
		val xor = odd[c] xor odd[d] xor toSwitch[x]
		p[cc] = dd
		odd[cc] = xor
		val old = minOf(d0[cc], d1[cc]) + minOf(d0[dd], d1[dd])
		if (xor == 1) {
			d0[dd] = d0[dd] + d1[cc]
			d1[dd] = d1[dd] + d0[cc]
		} else {
			d0[dd] = d0[dd] + d0[cc]
			d1[dd] = d1[dd] + d1[cc]
		}
		d0[dd] = minOf(d0[dd], k + 1)
		d1[dd] = minOf(d1[dd], k + 1)
		val new = minOf(d0[dd], d1[dd])
		ans.add(ans.last() - old + new)
	}
	return ans.drop(1)
}

fun main() {
	val (_, k) = readInts()
	val toSwitch = readLn().map { '1' - it }
	val where = List(toSwitch.size) { mutableListOf<Int>() }
	repeat(k) { i ->
		readLn()
		readInts().forEach { where[it - 1].add(i) }
	}
	println(solve(k, toSwitch, where).joinToString("\n"))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
