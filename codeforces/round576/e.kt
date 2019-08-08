package codeforces.round576

fun main() {
	val (n, rectNum) = readInts()
	val rectangles = Array(rectNum) { readInts() }
	val xSet = mutableSetOf(0, n)
	val ySet = mutableSetOf(0, n)
	for ((x1, y1, x2, y2) in rectangles) {
		xSet.add(x1 - 1)
		xSet.add(x2)
		ySet.add(y1 - 1)
		ySet.add(y2)
	}
	val xs = xSet.sorted()
	val ys = ySet.sorted()
	val m = xs.size + ys.size + 2
	val c = Array(m) { IntArray(m) }
	for (i in 0 until xs.size - 1) {
		c[m - 2][i] = xs[i + 1] - xs[i]
	}
	for (j in 0 until ys.size - 1) {
		c[xs.size + j][m - 1] = ys[j + 1] - ys[j]
	}
	for ((x1, y1, x2, y2) in rectangles) {
		for (i in xs.indices) {
			if ((xs[i] < x1 - 1) || (xs[i] >= x2)) continue
			for (j in ys.indices) {
				if ((ys[j] < y1 - 1) || (ys[j] >= y2)) continue
				c[i][xs.size + j] = minOf(xs[i + 1] - xs[i], ys[j + 1] - ys[j])
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
		prev.fill(-1)
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
		var flow = Integer.MAX_VALUE / 2
		var u = t
		while (u != s) {
			flow = minOf(flow, c[prev[u]][u] - f[prev[u]][u])
			u = prev[u]
		}
		u = t
		while (u != s) {
			f[prev[u]][u] += flow
			f[u][prev[u]] -= flow
			u = prev[u]
		}
		res += flow
	}
	return res
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
