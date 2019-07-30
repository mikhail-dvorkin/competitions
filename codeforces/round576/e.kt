package codeforces.round576

fun main() {
	val (n, rectNum) = readInts()
	val rects = Array(rectNum) { readInts() }
	val xSet = mutableSetOf(0, n)
	val ySet = mutableSetOf(0, n)
	for (rect in rects) {
		xSet.add(rect[0] - 1)
		xSet.add(rect[2])
		ySet.add(rect[1] - 1)
		ySet.add(rect[3])
	}
	val xs = xSet.toList().sorted()
	val ys = ySet.toList().sorted()
	val m = xs.size + ys.size + 2
	val c = Array(m) { IntArray(m) }
	for (i in 0 until xs.size - 1) {
		c[m - 2][i] = xs[i + 1] - xs[i]
	}
	for (j in 0 until ys.size - 1) {
		c[xs.size + j][m - 1] = ys[j + 1] - ys[j]
	}
	for (rect in rects) {
		for (i in 0 until xs.size) {
			if ((xs[i] < rect[0] - 1) or (xs[i] >= rect[2])) continue
			for (j in 0 until ys.size) {
				if ((ys[j] < rect[1] - 1) or (ys[j] >= rect[3])) continue
				c[i][xs.size + j] = minOf(xs[i + 1] - xs[i], ys[j + 1] - ys[j])
//				println("$i ${xs.size + j} : ${minOf(xs[i + 1] - xs[i], ys[j + 1] - ys[j])}")
			}
		}
	}
	println(edmonsKarp(c, m - 2, m - 1))
}

fun edmonsKarp(c: Array<IntArray>, s: Int, t: Int): Int {
	val n = c.size
	val f = Array(n) { IntArray(n) }
	val queue = IntArray(n)
	val prev = IntArray(n)
	var res = 0
	while (true) {
		queue[0] = s
		var low = 0
		var high = 1
		java.util.Arrays.fill(prev, -1)
		prev[s] = s
		while (low < high && prev[t] == -1) {
			val v = queue[low]
			low++
			for (u in 0 until n) {
				if (prev[u] != -1 || f[v][u] == c[v][u]) {
					continue
				}
				prev[u] = v
				queue[high] = u
				high++
			}
		}
		if (prev[t] == -1) {
			break
		}
		var flow = inf
		run {
			var u = t
			while (u != s) {
				flow = minOf(flow, c[prev[u]][u] - f[prev[u]][u])
				u = prev[u]
			}
		}
		var u = t
		while (u != s) {
			f[prev[u]][u] += flow
			f[u][prev[u]] -= flow
			u = prev[u]
		}
		res += flow
	}
	return res
}

val inf = Integer.MAX_VALUE / 2

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
