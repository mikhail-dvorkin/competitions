package atcoder.arc159

import java.util.TreeMap

fun main() {
	val tree = TreeMap<Int, Int>()
	tree[0] = 0

	fun get(x: Int): Int {
		val xLeft = tree.floorKey(x)
		val xRight = tree.ceilingKey(x)
		var result = Int.MAX_VALUE
		if (xLeft != null) {
			result = result.coerceAtMost(tree[xLeft]!! + x - xLeft)
		}
		if (xRight != null) {
			result = result.coerceAtMost(tree[xRight]!!)
		}
		return result
	}

	repeat(readInt()) {
		val (from, to) = readInts()
		val x = to
		val y = get(from - 1)
		if (get(x) <= y) return@repeat
		tree[x] = y
		while (true) {
			val xLeft = tree.floorKey(x - 1)
			if (xLeft == null || tree[xLeft]!! < y) break
			tree.remove(xLeft)
		}
		while (true) {
			val xRight = tree.ceilingKey(x + 1)
			if (xRight == null || tree[xRight]!! - xRight < y - x) break
			tree.remove(xRight)
		}
	}
	val x = tree.lastKey()!!
	println(x - get(x))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
