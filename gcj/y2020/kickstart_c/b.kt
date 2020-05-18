package gcj.y2020.kickstart_c

private fun solve(): String {
	val (hei, wid) = readInts()
	val a = List(hei) { readLn() }
	val all = a.joinToString("").toSet()
	val ans = mutableSetOf<Char>()
	while (ans != all) {
		val unstable = mutableSetOf<Char>()
		for (y in 0..hei - 2) for (x in 0 until wid) {
			val here = a[y][x]
			val below = a[y + 1][x]
			if (here != below && below !in ans) unstable.add(here)
		}
		ans.add((all - ans - unstable).firstOrNull() ?: return "-1")
	}
	return ans.joinToString("")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
