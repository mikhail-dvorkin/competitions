package gcj.y2020.kickstart_c

import java.lang.StringBuilder

private fun solve(): String {
	val (hei, wid) = readInts()
	val a = List(hei) { readLn() }
	val all = a.joinToString("").toSet()
	val ans = StringBuilder()
	while (ans.length < all.size) {
		val unstable = mutableSetOf<Char>()
		for (y in 0 until hei - 1) for (x in 0 until wid) {
			val here = a[y][x]
			val below = a[y + 1][x]
			if (here != below && below !in ans) unstable.add(here)
		}
		val toBuild = (all - ans.toSet() - unstable).firstOrNull() ?: return "-1"
		ans.append(toBuild)
	}
	return ans.toString()
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
