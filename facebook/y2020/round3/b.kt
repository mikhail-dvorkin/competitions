package facebook.y2020.round3

import java.util.*

private fun solve(): Int {
	val (n, m, k, x) = readInts()
	val (sIn, pIn) = listOf(n, m).map { length ->
		val array = readInts().toIntArray() + IntArray(length - k) { 0 }
		val (a, b, c, d) = readInts()
		for (i in k until length) {
			array[i] = ((a.toLong() * array[i - 2] + b.toLong() * array[i - 1] + c) % d).toInt() + 1
		}
		array
	}

	val set = TreeMap<Int, Deque<Int>>()
	fun add(size: Int) {
		val r = size % x
		set.putIfAbsent(r, LinkedList())
		set[r]!!.addLast(size)
	}
	fun remove(p: Int): Int {
		val r = p % x
		val q = set.ceilingKey(r) ?: set.ceilingKey(0)
		val size = set[q]!!.pollFirst()
		if (set[q]!!.isEmpty()) {
			set.remove(q)
		}
		return (size - p) / x
	}

	val sizes = sIn.sortedDescending()
	val sizesLarge = sizes.filter { it >= x }
	val sizesSmall = sizes.filter { it < x }
	val packages = pIn.sorted()
	var low = 0
	var high = minOf(n, m) + 1
	while (low + 1 < high) {
		val mid = (low + high) / 2
		var i = 0
		var j = 0
		val deque = LinkedList<Int>()
		var keys = 0L
		var bad = false
		for (p in packages.take(mid).reversed()) {
			while ((i < sizesLarge.size) && (sizesLarge[i] >= p)) {
				add(sizesLarge[i])
				i++
			}
			while ((j < sizesSmall.size) && (sizesSmall[j] >= p)) {
				deque.addLast(sizesSmall[j])
				j++
			}
			if (set.isEmpty()) {
				if (p < x) {
					if (deque.isNotEmpty()) {
						deque.removeLast()
						continue
					}
				}
				bad = true
				break
			}
			keys += remove(p)
		}
		while (i < sizesLarge.size) {
			add(sizesLarge[i])
			i++
		}
		while (j < sizesSmall.size) {
			deque.addLast(sizesSmall[j])
			j++
		}
		while (set.isNotEmpty()) {
			keys += remove(0)
		}
		while (deque.isNotEmpty()) {
			deque.removeLast()
			keys++
		}
		if (bad || keys < n - 1) high = mid else low = mid
	}
	return low
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
