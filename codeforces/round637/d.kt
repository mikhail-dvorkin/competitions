package codeforces.round637

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val ans = mutableListOf<Pair<Int, Int>>()
	fun dfs(v: Int, p: Int, tIn: Int) {
		ans.add(v to tIn)
		val kids = nei[v].minus(p)
		val jump = maxOf(kids.size - tIn + 1, 0)
		for (i in kids.indices) {
			val tOut = tIn + i + if (i < jump) 1 else -kids.size
			if (i == jump) ans.add(v to tOut - 1)
			dfs(kids[i], v, tOut)
			ans.add(v to tOut)
		}
		if (p >= 0 && jump == kids.size) ans.add(v to tIn - 1)
	}
	dfs(0, -1, 0)
	println(ans.size)
	println(ans.joinToString("\n") { "${it.first + 1} ${it.second}" })
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
