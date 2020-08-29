package facebook.y2020.round2

import java.util.*

private fun solve(MOD: Int = 1_000_000_007, maxWeight: Int = 1_000_000_000): Int {
	val (n, m, events, k) = readInts()
	val edges = n * m + n
	val (x, y, ids, weights) = listOf(m to n, m to n, edges to events, maxWeight to events).map { (modulo, length) ->
		val array = readInts().toIntArray() + IntArray(length - k) { 0 }
		val (a, b, c) = readInts()
		for (i in k until length) {
			array[i] = ((a.toLong() * array[i - 2] + b.toLong() * array[i - 1] + c) % modulo).toInt()
		}
		array
	}
	val (short, long) = List(2) { List(n) { TreeSet<Long>() } }
	val main = TreeSet<Long>()
	val alter = LongArray(n) { -1L }
	val selected = LongArray(n) { -1L }
	fun isShort(cycle: Int, j: Int): Boolean {
		return x[cycle] <= j && j < y[cycle]
	}
	for (cycle in x.indices) {
		val (min, max) = listOf(x[cycle], y[cycle]).sorted()
		x[cycle] = min; y[cycle] = max
		for (j in 0 until m) {
			val id = cycle * m + j
			if (isShort(cycle, j)) {
				short[cycle].add(1L * edges + id)
			} else {
				long[cycle].add(1L * edges + id)
			}
		}
		if (short[cycle].isNotEmpty()) {
			alter[cycle] = short[cycle].last()
			main.add(alter[cycle])
		}
		selected[cycle] = long[cycle].last()
		main.add(1L * edges + n * m + cycle)
	}
	var ans = 1
	val w = IntArray(edges) { 1 }
	var sum = edges.toLong()
	var removed = (n + 1).toLong()
	for (event in ids.indices) {
		val id = ids[event]
		val oldWeight = w[id]
		val newWeight = weights[event]
		removed -= main.last() / edges
		if (id < n * m) {
			val cycle = id / m
			val j = id % m
			val set = if (isShort(cycle, j)) short[cycle] else long[cycle]
			if (!set.remove(oldWeight.toLong() * edges + id)) error("")
			set.add(newWeight.toLong() * edges + id)
			val alt = alter[cycle]
			val sel = selected[cycle]
			removed -= sel / edges
			if (alt != -1L) {
				if (!main.remove(alt)) error("")
				val a1 = short[cycle].last()
				val a2 = long[cycle].last()
				alter[cycle] = minOf(a1, a2)
				main.add(alter[cycle])
				selected[cycle] = maxOf(a1, a2)
			} else {
				selected[cycle] = long[cycle].last()
			}
			removed += selected[cycle] / edges
		} else {
			main.remove(oldWeight.toLong() * edges + id)
			main.add(newWeight.toLong() * edges + id)
		}
		removed += main.last() / edges
		sum += newWeight - oldWeight
		w[id] = newWeight
		ans = ((ans * ((sum - removed) % MOD)) % MOD).toInt()
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
