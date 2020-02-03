package codeforces.round616

import kotlin.random.Random

fun main() {
	val rnd = Random(566)
	while (true) {
		val n = rnd.nextInt(32)
		val k = rnd.nextInt(12)
		val toSwitch = List(n) { rnd.nextInt(2) }
		val where = List(n) {
			val len = rnd.nextInt(minOf(3, k + 1))
			val set = mutableSetOf<Int>()
			while (set.size < len) {
				set.add(rnd.nextInt(k))
			}
			set.toList()
		}
		val b = solveDumb(k, toSwitch, where)
		if (b.any { it > k }) continue
		val a = solve(k, toSwitch, where)
		println("$a\t$b")
		if (a != b) return println("$n $k $toSwitch $where")
	}
}

private fun solveDumb(k: Int, toSwitch: List<Int>, where: List<List<Int>>): List<Int> {
	return toSwitch.indices.map { x ->
		(0 until (1 shl k)).filter { m ->
			(0..x).all { i ->
				(toSwitch[i] + where[i].sumBy { s -> (m shr s) and 1 }) % 2 == 0
			}
		}.map(Integer::bitCount).min() ?: k + 1
	}
}
