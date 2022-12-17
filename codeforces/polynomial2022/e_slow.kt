package codeforces.polynomial2022

fun main() {
	val (n, d) = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v)
		nei[v].add(u)
	}
	val arrays = List(2) { readInts().drop(1).map { it - 1 } }
	val needed = List(2) { BooleanArray(n).also { it[0] = true } }
	for (t in 0..1) {
		for (x in arrays[t]) needed[t][x] = true
		val stack = IntArray(n)
		var stackSize = 0
		fun dfs(v: Int, p: Int) {
			stack[stackSize++] = v
			if (needed[t][v] && stackSize > d) {
				needed[t xor 1][stack[stackSize - 1 - d]] = true
			}
			for (u in nei[v]) if (u != p) {
				dfs(u, v)
			}
			stackSize--
		}
		dfs(0, -1)
	}
	var ans = 0
	for (t in 0..1) {
		var travel = 0
		fun dfs(v: Int, p: Int): Boolean {
			var result = needed[t][v]
			for (u in nei[v]) if (u != p) {
				if (dfs(u, v)) result = true
			}
			if (result) travel++
			return result
		}
		dfs(0, -1)
		ans += (travel - 1) * 2
	}
	println(ans)
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
