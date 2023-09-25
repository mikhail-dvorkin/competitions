package facebook.y2023.qual

private fun solve(): Long {
	val (n, m) = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}

	val mark = BooleanArray(n)
	val isBridge = BooleanArray(n)
	val parent = IntArray(n)
	val depth = IntArray(n)
	val tIn = IntArray(n)
	val fUp = IntArray(n)
	var time = 0
	val dfsBridges = DeepRecursiveFunction { v ->
		mark[v] = true
		tIn[v] = time
		fUp[v] = time
		time++
		for (u in nei[v]) {
			if (u == parent[v]) continue
			if (mark[u]) {
				fUp[v] = minOf(fUp[v], tIn[u])
				continue
			}
			depth[u] = depth[v] + 1
			parent[u] = v
			callRecursive(u)
			fUp[v] = minOf(fUp[v], fUp[u])
			if (fUp[u] > tIn[v]) {
				isBridge[u] = true
//				println("$u $v")
			}
		}
	}
	parent[0] = -1
	dfsBridges(0)
	fun isBridge(u: Int, v: Int) = parent[u] == v && isBridge[u] || parent[v] == u && isBridge[v]

	val color = BooleanArray(n)
	val compLeader = IntArray(n)
	mark.fill(false)
	var isOdd: Boolean

	val dfsOdd = DeepRecursiveFunction { v ->
		mark[v] = true
		for (u in nei[v]) {
			if (isBridge(u, v)) continue
			if (mark[u]) {
				if (color[u] == color[v]) isOdd = true
				continue
			}
			color[u] = !color[v]
			compLeader[u] = v
			callRecursive(u)
		}
	}
	val oddLeaders = mutableSetOf<Int>()
	for (v in nei.indices) if (!mark[v]) {
		isOdd = false
		compLeader[v] = v
		dfsOdd(v)
		if (isOdd) oddLeaders.add(v)
	}
//	println(oddLeaders)

	val deque = ArrayDeque<Int>()
	val processed = BooleanArray(n)
	val inf = Int.MAX_VALUE / 2
	val dist = IntArray(n) { inf }
	for (v in nei.indices) if (compLeader[v] in oddLeaders) {
		deque.add(v)
		dist[v] = 0
	}
	while (deque.isNotEmpty()) {
		val v = deque.removeFirst()
		if (processed[v]) continue
		processed[v] = true
		for (u in nei[v]) {
			val edgeWeight = if (isBridge(u, v)) 1 else 0
			val newDist = dist[v] + edgeWeight
			if (newDist < dist[u]) {
				dist[u] = newDist
				if (edgeWeight == 1) deque.add(u) else deque.addFirst(u)
			}
		}
	}
//	println(dist.contentToString())

	val memo = MutableList<Pair<Int, Int>?>(n.countSignificantBits() * n) { null }
	fun ancestorUp(v: Int, levels: Int): Pair<Int, Int> {
		if (levels == 0) return v to dist[v]
		if (levels == 1) return parent[v] to minOf(dist[v], dist[parent[v]])
		val two = levels.takeHighestOneBit()
		if (two != levels) {
			val (u, minU) = ancestorUp(v, two)
			val (w, minW) = ancestorUp(u, levels - two)
			return w to minOf(minU, minW)
		}
		val code = (levels.countTrailingZeroBits() - 1) * n + v
		return memo.computeIfNull(code) {
			val (u, minU) = ancestorUp(v, two / 2)
			val (w, minW) = ancestorUp(u, two / 2)
			w to minOf(minU, minW)
		}
	}
	fun ancestor(v: Int, level: Int): Pair<Int, Int> {
		require(level in 0..depth[v])
		return ancestorUp(v, depth[v] - level)
	}
//	tailrec fun ancestor(v: Int, level: Int): Int {
//		require(level <= depth[v])
//		if (depth[v] == level) return v
//		return ancestor(parent[v], level)
//	}
//	fun minDist(v: Int, level: Int): Int {
//		require(level <= depth[v])
//		if (depth[v] == level) return dist[v]
//		return minOf(dist[v], minDist(parent[v], level))
//	}

	return List(readInt()) {
		val (a, b) = readInts().map { it - 1 }
		val cLevel = (0..(minOf(depth[a], depth[b]) + 1)).binarySearch { d ->
			val aa = ancestor(a, d).first
			val bb = ancestor(b, d).first
			aa != bb
		} - 1
//		println(ancestor(a, cLevel))
		val x = ancestor(a, cLevel).second
		val y = ancestor(b, cLevel).second
		(minOf(x, y).takeIf { it != inf } ?: -1).toLong()
	}.sum()
}

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}
private fun Int.countSignificantBits() = Int.SIZE_BITS - Integer.numberOfLeadingZeros(this)
private inline fun <E> MutableList<E?>.computeIfNull(key: Int, defaultValue: () -> E) = get(key)?.also { return it } ?: defaultValue().also { set(key, it) }

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
