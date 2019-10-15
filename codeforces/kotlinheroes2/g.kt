private fun solve() {
	val n = readInt()
	val flag = readInts()
	val want = readInts()
	val changed = flag.zip(want).map { it.first != it.second }
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
		nei[b].add(a)
	}
	if (changed.all { !it }) {
		println("Yes\n0")
		return
	}
	val w = changed.indexOfFirst { it }
	val p = MutableList(n) { 0 }
	val u = furthest(nei, p, changed, w)
	val v = furthest(nei, p, changed, u)
	val path = mutableListOf(v)
	while (path.last() != u) path.add(p[path.last()])
	println(check(flag, want, path) ?: check(flag, want, path.reversed()) ?: "No")
}

private fun check(flag: List<Int>, want: List<Int>, path: List<Int>): String? {
	val f = flag.toMutableList()
	val save = f[path.first()]
	for ((a, b) in path.zipWithNext()) {
		f[a] = f[b]
	}
	f[path.last()] = save
	if (f != want) return null
	return "Yes\n" + path.size.toString() + "\n" + path.joinToString(" ") { (it + 1).toString() }
}

private fun furthest(nei: List<MutableList<Int>>, p: MutableList<Int>, cool: List<Boolean>, v: Int): Int {
	p.fill(-1)
	val t = dfs(nei, p, cool, v, -1)
	return t.second
}

private fun dfs(nei: List<MutableList<Int>>, p: MutableList<Int>, cool: List<Boolean>, v: Int, parent: Int): Pair<Int, Int> {
	var best = if (cool[v]) 0 to v else -nei.size to -1
	p[v] = parent
	for (u in nei[v]) {
		if (u == parent) continue
		val (dist, vertex) = dfs(nei, p, cool, u, v)
		if (dist + 1 > best.first) best = dist + 1 to vertex
	}
	return best
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
