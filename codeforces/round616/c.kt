package codeforces.round616

import kotlin.random.Random

fun main() {
	stress()
	val (_, k) = readInts()
	val init = readLn().map { it == '1' }
	val where = List(init.size) { mutableListOf<Int>() }
	repeat(k) { j ->
		readLn()
		val set = readInts().map { it - 1 }
		for (i in set) {
			where[i].add(j)
		}
	}
	println(solve(k, init, where).joinToString("\n"))
}

private fun solve(k: Int, init: List<Boolean>, where: List<MutableList<Int>>): List<Int> {
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
	val inf = 300 * (init.size + k)
	val rnd = Random(566)
	for (x in init.indices) {
		if (where[x].size == 0) {
			ans.add(ans.last())
			continue
		}
		if (where[x].size == 1) {
			val (c) = where[x]
			val cc = get(c)
			val xor = odd[c] xor (if (init[x]) 0 else 1)
			val old = minOf(d0[cc], d1[cc])
			if (xor == 1) {
				d0[cc] = inf
			} else {
				d1[cc] = inf
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
		val xor = odd[c] xor odd[d] xor (if (init[x]) 0 else 1)
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
		d0[dd] = minOf(d0[dd], inf)
		d1[dd] = minOf(d1[dd], inf)
		val new = minOf(d0[dd], d1[dd])
		ans.add(ans.last() - old + new)
	}
	return ans.drop(1)
}

private fun solveDumb(k: Int, init: List<Boolean>, where: List<MutableList<Int>>): List<Int> {
	val inf = 300 * (init.size + k)
	val ans = mutableListOf<Int>()
	for (x in init.indices) {
		var best = inf
		for (m in 0 until (1 shl k)) {
			var good = true
			for (i in 0..x) {
				var v = if (init[i]) 1 else 0
				for (s in where[i]) {
					if (((m shr s) and 1) == 1) {
						v = v xor 1
					}
				}
				if (v != 1) { good = false; break }
			}
			if (good) best = best.coerceAtMost(Integer.bitCount(m))
		}
		ans.add(best)
	}
	return ans
}

private fun stress() {
	val rnd = Random(566)
	while (true) {
		val n = rnd.nextInt(32)
		val k = rnd.nextInt(12)
		val init = List(n) { rnd.nextBoolean() }
		val inf = 300 * (init.size + k)
		val where = List(n) {
			val len = rnd.nextInt(minOf(3, k + 1))
			val set = mutableSetOf<Int>()
			while (set.size < len) {
				set.add(rnd.nextInt(k))
			}
			set.toMutableList()
		}
		val b = solveDumb(k, init, where)
		if (b.any { it >= inf }) continue
		val a = solve(k, init, where)
		print(a)
		println(b)
		if (a != b) {
			println(n)
			println(k)
			println(init)
			println(where)
			return
		}
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
