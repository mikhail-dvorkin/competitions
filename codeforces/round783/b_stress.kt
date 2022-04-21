package codeforces.round783

import kotlin.math.max
import kotlin.math.sign
import kotlin.random.Random
import kotlin.random.nextInt

private fun solveDumb(a: List<Int>): Int {
	val n = a.size
	var dumb = -n
	for (mask in 0 until (1 shl (n - 1))) {
		val p = List(n - 1) { (mask shr it) % 2 == 1 }
		var value = 0
		for (i in 0 until n) {
			if (i == 0 || p[i - 1]) {
				var j = i
				var sum = a[i]
				while (j + 1 < n && !p[j]) {
					j++
					sum += a[j]
				}
				value += (j + 1 - i) * sum.sign
			}
		}
		dumb = max(dumb, value)
	}
	return dumb
}

fun stress(a: List<Int>): Boolean {
	val smart = solve(a)
	val dumb = solveDumb(a)
	println("$a $smart $dumb")
	return dumb != smart
}

fun stress() {
	val random = Random(239566)
	while (true) {
		val a = List(5) { random.nextInt(-5..5) }
		if (stress(a)) {
			stress(a)
			return
		}
	}
}

fun main() {
	stress()
}
