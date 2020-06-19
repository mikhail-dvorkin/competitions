package codeforces.globalround8

import kotlin.math.pow

fun main() {
	val n = readInt()
	val on = BooleanArray(n)
	if (n < 4) return println(0)
	val c = n.toDouble().pow(0.5).toInt()
	val half = (n + c - 1) / c
	for (iter in 1..9999) {
		val move = on.indices.filter { !on[it] && it % half > 0 }.take(half)
		if (move.isEmpty()) break
		for (x in move) on[x] = true
		println("${move.size} ${move.map { it + 1 }.joinToString(" ")}")
		val x = readInt() - 1
		repeat(move.size) { on[(x + it) % n] = false }
	}
	println(0)
}

private fun byTwo(n: Int) {
	val on = BooleanArray(n)
	val half = n / 2
	while (true) {
		val before = on.count { it }
		if (before >= half - 1) return println(0)
		val touched = on.indices.filter { on[it] && it < 2 * half }.map { it % half }.toMutableSet()
		val touchNew = (0 until half).first { it !in touched }
		touched.add(touchNew)
		val move = touched.flatMap { listOf(it, half + it) }.filter { !on[it] }
		for (x in move) on[x] = true
		println("${move.size} ${move.map { it + 1 }.joinToString(" ")}")
		val x = readInt() - 1
		repeat(move.size) { on[(x + it) % n] = false }
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
