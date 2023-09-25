package facebook.y2023.qual

private fun solve(): Int {
	readInt()
	val a = readInts().sorted()
	if (a.size == 1) return a[0]
	val inf = Int.MAX_VALUE
	val ans = listOf(a[0] + a.last(), a[0] + a[a.size - 2], a[1] + a.last()).minOf { sum ->
		val pool = a.groupBy {it}.mapValues { it.value.size }.toMutableMap()
		fun remove(v: Int) {
			if (pool[v] == 1) pool.remove(v) else pool[v] = pool[v]!! - 1
		}
		val unpaired = mutableListOf<Int>()
		while (pool.isNotEmpty()) {
			val v = pool.iterator().next().key
			remove(v)
			val u = sum - v
			if (u in pool) {
				remove(u)
				continue
			}
			unpaired.add(v)
		}
		if (unpaired.size != 1) return@minOf inf
		(sum - unpaired[0]).takeIf { it > 0 } ?: inf
	}
	return if (ans == inf) -1 else ans
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
