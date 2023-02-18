package atcoder.arc156

fun main() {
	val n = readInt()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val alive = BooleanArray(n) { true }
	while (true) {
		val countAlive = alive.count { it }
		if (countAlive <= 2) break
		val toKill = nei.indices.filter { v ->
			nei[v].count { alive[it] } == 1
		}
		for (v in toKill) alive[v] = false
	}
	val v0 = alive.indexOf(true)
	val u0 = nei[v0].first()
	val list = mutableListOf<Int>()
	val order = mutableListOf<Int>()
	fun dfs(v: Int, p: Int) {
		for (u in nei[v]) {
			if (u == p) continue
			dfs(u, v)
		}
		list.add(v)
	}
	dfs(v0, u0)
	order.addAll(list)
	list.clear()
	dfs(u0, v0)
	order.addAll(list.reversed())
	val p = IntArray(n)
	for (i in 0 until n) {
		p[order[i]] = order[n - 1 - i]
	}
	println(p.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
