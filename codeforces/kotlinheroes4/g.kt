package codeforces.kotlinheroes4

import kotlin.math.abs
import kotlin.random.Random

fun main() {
	readLn()
	val p = readInts()
	val x = readInts().sorted()
	val r = Random(566)
	if (x.size == 2) {
		println("YES")
		println("${x[0]} ${p[0]}")
		println("${x[1]} ${p[0]}")
		return
	}
	repeat(128) {
		val a1 = r.nextInt(x.size)
		val a2 = (r.nextInt(x.size - 1) + a1 + 1) % x.size
		val diff = abs(x[a1] - x[a2])
		for (ap in p) {
			if (diff % ap != 0) continue
			var rem = x[a1] % ap
			val other = x.filter { it % ap != rem }
			val gcd = other.zipWithNext { a, b -> b - a }.fold(0, ::gcd)
			val bp = p.firstOrNull { gcd % it == 0 }
			if (bp != null) {
				val bPhase = ((other.firstOrNull() ?: 0) + bp - 1) % bp + 1
				val aPhase = (rem + ap - 1) % ap + 1
				println("YES")
				println("$aPhase $ap")
				println("$bPhase $bp")
				return
			}
		}
	}
	println("NO")
}

private tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
