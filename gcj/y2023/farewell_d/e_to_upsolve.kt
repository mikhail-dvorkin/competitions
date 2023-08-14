package gcj.y2023.farewell_d

import kotlin.random.Random

fun generate(n: Int, m: Int): MutableList<Pair<Int, Int>> {
	var toAdd = m
	val list = mutableListOf<Pair<Int, Int>>()
	fun addEdge(v: Int, u: Int) {
		toAdd--
		list.add(v to u)
	}
	for (i in 0 until n) {
		addEdge(i, (i + 1) % n)
	}
	for (i in 2 until n) {
//		val canAdd = i - 1
//		if (canAdd >= toAdd) {
//			for (j in 0..i - 2) addEdge(i, j)
//			continue
//		}
		for (j in i - 2 downTo 0) {
			if (toAdd == 0) break
			addEdge(i, j)
		}
	}
	return list
}

private fun solve(nei: List<MutableList<Int>>): MutableList<Int> {
	val n = nei.size
	val s = nei.indices.minBy { nei[it].size }
	val used = BooleanArray(n)
	val path = mutableListOf(s)
	used[s] = true
	fun grow() {
		while (nei[path.last()].size == 2) {
			val v = nei[path.last()].firstOrNull { !used[it] } ?: break
			path.add(v)
			used[v] = true
		}
	}
	grow()
	path.reverse()
	grow()
	fun grow2() {
		while (path.size < n) {
			val v = nei[path.last()].firstOrNull { !used[it] } ?: error("")
			path.add(v)
			used[v] = true
		}
	}
//	if (nei[path.last()].size < nei[path.first()].size) path.reverse()
	grow2()
//	for (i in path.indices) if (!nei[path[i]].contains(path[(i + 1) % n])) {
//		System.setOut(java.io.PrintStream("shit.txt"))
//		println(nei)
//		println(path)
//		println()
//		for (i in 0 until n) for (j in 0 until i) if (nei[i].contains(j)) println("${i + 1} ${j + 1}")
//		System.out.flush()
//		System.exit(1)
//	}
	return path
}

private fun solve() {
	val (n, m) = readInts()
	val generated = generate(n, m)
	println(generated.joinToString("\n") { "${it.first + 1} ${it.second + 1}" })

	val nei = List(n) { mutableListOf<Int>() }
	fun addEdge(v: Int, u: Int) {
		nei[v].add(u); nei[u].add(v)
	}
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		addEdge(u, v)
	}

	val path = solve(nei)
	println(path.map { it + 1 }.joinToString(" "))
}

fun main() {
	repeat(readInt()) { solve() }
//	research(6, 10, 10000)
//	for (n in 3..20) for (m in n..n * (n - 1) / 2) research(n, m)
//	for (n in 1000..1000) for (m in n..n+10) research(n, m)
}

fun research(n: Int, m: Int, times: Int = 100) {
	val graph = generate(n, m)
	repeat(times) {
		val random = Random(it)
		val p = (0 until n).toList().shuffled(random)
		println("//" + p)

		val nei = List(n) { mutableListOf<Int>() }
		fun addEdge(v: Int, u: Int) {
			nei[v].add(u); nei[u].add(v)
		}

		for (edge in graph) {
			addEdge(p[edge.first], p[edge.second])
		}

		val path = solve(nei)
		require(path.size == n)
		require(path.toSet() == (0 until n).toSet())
		for (i in path.indices) require(nei[path[i]].contains(path[(i + 1) % n]))
		println(path.map { it + 1 }.joinToString(" "))
	}
}

public inline fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
	val iterator = iterator()
	if (!iterator.hasNext()) throw NoSuchElementException()
	var minElem = iterator.next()
	if (!iterator.hasNext()) return minElem
	var minValue = selector(minElem)
	do {
		val e = iterator.next()
		val v = selector(e)
		if (minValue > v) {
			minElem = e
			minValue = v
		}
	} while (iterator.hasNext())
	return minElem
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
