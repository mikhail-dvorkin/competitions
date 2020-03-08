package codeforces.round626

import java.io.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private fun solve() {
	var st = StringTokenizer(`in`.readLine())
	val n = st.nextToken().toInt()
	val m = st.nextToken().toInt()
	val c = LongArray(n)
	st = StringTokenizer(`in`.readLine())
	val nei: Array<ArrayList<Int>> = Array(n) { ArrayList() }
	for (i in 0 until n) {
		c[i] = st.nextToken().toLong()
	}
	for (i in 0 until m) {
		st = StringTokenizer(`in`.readLine())
		val u = st.nextToken().toInt() - 1
		val v = st.nextToken().toInt() - 1
		nei[v].add(u)
	}
	val cSum: MutableMap<Long, Long> = HashMap()
	for (i in 0 until n) {
		if (nei[i].isEmpty()) continue
		nei[i].sort()
		var p = nei[i].size.toLong()
		for (x in nei[i]) {
			p = mix(p shl 16 or x.toLong())
		}
		cSum[p] = c[i] + cSum.getOrDefault(p, 0L)
	}
	var ans: Long = 0
	for (xx in cSum.values) {
		var x = xx
		while (x > 0) {
			val t = ans % x
			ans = x
			x = t
		}
	}
	out.println(ans)
}

fun mix(x: Long): Long {
	var xx = x
	xx = xx xor (xx ushr 33)
	xx *= -0xae502812aa7333L
	xx = xx xor (xx ushr 33)
	xx *= -0x3b314601e57a13adL
	xx = xx xor (xx ushr 33)
	return xx
}

private val `in` = BufferedReader(InputStreamReader(System.`in`))
private val out = PrintWriter(System.out)

fun main() {
	val tests = `in`.readLine().toInt()
	for (test in 0 until tests) {
		if (test > 0) `in`.readLine()
		solve()
	}
	out.close()
}
