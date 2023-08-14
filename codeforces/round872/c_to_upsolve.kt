package codeforces.round783

import java.util.*


fun main() {
	val n = readInt()
	val a = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v); nei[v].add(u)
	}
	val price = IntArray(n)
	val niceXor = IntArray(n)
	val temp = TreeSet<Int>()
	val nice = MutableList(n) { temp }
	fun dfs(v: Int, p: Int) {
		var kids = 0
		for (u in nei[v]) if (u != p) {
			dfs(u, v)
			price[v] += price[u]
			kids++
		}
		if (kids == 0) {
			nice[v] = TreeSet<Int>().apply { add(a[v]) }
			return
		}
		if (kids == 1) {
			val u = nei[v].first { it != p }
			nice[v] = nice[u]
			niceXor[v] = niceXor[u] xor a[v]
			return
		}
		val uu = nei[v].maxBy { nice[it].size }
		val counter = TreeMap<Int, Int>()
		for (u in nei[v]) if (u != p && u != uu) {
			for (x in nice[u]) {
				val y = x xor niceXor[u]
				counter[y] = counter.getOrDefault(y, 0) + 1
			}
		}
		val maxCount = counter.entries.maxOf { it.value }
		val other = TreeSet(counter.filter { it.value == maxCount }.map { it.key })
		val niceUU = nice[uu]
		val niceXorUU = niceXor[uu]
		fun unite(set1: TreeSet<Int>, xor1: Int, set2: TreeSet<Int>, xor2: Int) {
			if (set1.size < set2.size) return unite(set2, xor2, set1, xor1)
			val xor12 = xor1 xor xor2
			val common = TreeSet<Int>()
			for (x in set2) {
				if (set1.contains(x xor xor12)) common.add(x xor xor2)
			}
			price[v] = kids - maxCount
			if (common.isNotEmpty()) {
				price[v]--
				nice[v] = common
				return
			}
			for (x in set2) {
				set1.add(x xor xor12)
			}
			nice[v] = set1
			niceXor[v] = xor1
		}
		unite(niceUU, niceXorUU, other, 0)
		niceXor[v] = niceXor[v] xor a[v]
	}
	dfs(0, -1)
	println(price[0])
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
