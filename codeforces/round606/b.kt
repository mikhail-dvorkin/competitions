package codeforces.round606

fun main() = repeat(readInt()) {
	val data = readInts()
	val n = data[0]
	val m = data[1]
	val a = data[2] - 1
	val b = data[3] - 1
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v)
		nei[v].add(u)
	}
	val mark = IntArray(n) { -1 }
	val compTouch = IntArray(n) { 0 }
	var comp = 0
	fun dfs(v: Int) {
		mark[v] = comp
		for (u in nei[v]) {
			if (u == a) {
				compTouch[comp] = compTouch[comp] or 1
				continue
			}
			if (u == b) {
				compTouch[comp] = compTouch[comp] or 2
				continue
			}
			if (mark[u] == -1) dfs(u)
		}
	}
	for (i in 0 until n) {
		if (mark[i] == -1 && i != a && i != b) {
			dfs(i)
			comp++
		}
	}
	val (aCount, bCount) = listOf(1, 2).map { mask -> (0 until n).filter { mark[it] >= 0 && compTouch[mark[it]] == mask }.size }
	println(aCount.toLong() * bCount)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
