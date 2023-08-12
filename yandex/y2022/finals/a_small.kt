package yandex.y2022.finals

import java.util.*
import kotlin.math.sign

fun main() {
	readLn()
	val our = readInts()
	val their = readInts()
	val e = List(our.size) { i -> IntArray(their.size) { j -> -(our[i] - their[j]).sign } }
	val h = hungarian(e)
	println(-h)
}

fun hungarian(e: List<IntArray>): Int {
	val ans = IntArray(e.size)
	Arrays.fill(ans, -1)
	val infty = Int.MAX_VALUE / 3
	var swap = false
	val n1 = e.size
	val n2 = e[0].size
	val u = IntArray(n1 + 1)
	val v = IntArray(n2 + 1)
	val p = IntArray(n2 + 1)
	val way = IntArray(n2 + 1)
	for (i in 1..n1) {
		p[0] = i
		var j0 = 0
		val minv = IntArray(n2 + 1)
		Arrays.fill(minv, infty)
		val used = BooleanArray(n2 + 1)
		do {
			used[j0] = true
			val i0 = p[j0]
			var j1 = 0
			var delta = infty
			for (j in 1..n2) {
				if (!used[j]) {
					val cur = e[i0 - 1][j - 1] - u[i0] - v[j]
					if (cur < minv[j]) {
						minv[j] = cur
						way[j] = j0
					}
					if (minv[j] < delta) {
						delta = minv[j]
						j1 = j
					}
				}
			}
			for (j in 0..n2) {
				if (used[j]) {
					u[p[j]] += delta
					v[j] -= delta
				} else {
					minv[j] -= delta
				}
			}
			j0 = j1
		} while (p[j0] != 0)
		do {
			val j1 = way[j0]
			p[j0] = p[j1]
			j0 = j1
		} while (j0 > 0)
	}
	var sum = 0
	for (j in 1..n2) {
		if (p[j] > 0) {
			// if (e[p[j] - 1][j - 1] >= infty) no matching of size n1;
			sum += e[p[j] - 1][j - 1];
			if (swap) {
				ans[j - 1] = p[j] - 1
			} else {
				ans[p[j] - 1] = j - 1
			}
		}
	}
	return sum
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
