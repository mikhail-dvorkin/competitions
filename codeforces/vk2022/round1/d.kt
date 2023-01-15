package codeforces.vk2022.round1

import kotlin.math.roundToLong
import kotlin.math.sqrt

private fun solve() {
	readln()
	val a = readInts()
	var ans = 1
	val toTest = mutableSetOf<Long>()
	for (i in a.indices) for (j in 0 until i) {
		val ba = a[i] - a[j]
		for (d in 1..ba) {
			if (d * d > ba) break
			if (ba % d != 0) continue
			val e = ba / d
			if (((d xor e) and 1) != 0) continue
			val q = (d + e) / 2
			val x = q.toLong() * q - a[i]
			if (x >= 0) toTest.add(x)
		}
	}
	for (x in toTest) {
		val current = a.count { b ->
			val y = b + x
			val z = sqrt(y.toDouble()).roundToLong()
			y == z * z
		}
		ans = ans.coerceAtLeast(current)
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
