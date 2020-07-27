package facebook.y2020.qual

private fun solve(): Int {
	val n = readInt()
	val growsHere = mutableMapOf<Int, Int>()
	val fallsHere = mutableMapOf<Int, MutableList<Int>>()
	repeat(n) {
		val (p, h) = readInts()
		growsHere[p] = h
		fallsHere.computeIfAbsent(p - h) { mutableListOf() }.add(h)
	}
	val best = mutableMapOf<Int, Int>()
	val bestWithoutThis = mutableMapOf<Int, Int>()
	for (x in (growsHere.keys + fallsHere.keys).sorted()) {
		val b = best.getOrDefault(x, 0)
		val bw = bestWithoutThis.getOrDefault(x, 0)
		for (h in fallsHere.getOrElse(x) { mutableListOf() }) {
			best[x + h] = maxOf(best.getOrDefault(x + h, 0), maxOf(b, bw) + h)
		}
		val h = growsHere[x] ?: continue
		bestWithoutThis[x + h] = maxOf(bestWithoutThis.getOrDefault(x + h, 0), bw + h)
	}
	return (best.values + bestWithoutThis.values).max()!!
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
