package codeforces.kotlinheroes10

val precalc = precalc()

fun precalc(): List<IntArray> {
	val precalc = mutableListOf<IntArray>()
	precalc.add(intArrayOf(0, 1))
	repeat(10) {
		val a = precalc.last()
		val b = IntArray(a.size * 2 - 1)
		for (i in a.indices) {
			b[2 * i] = a[i] + a.size - 1
			if (i > 0) {
				b[2 * i - 1] = i - 1
			}
		}
		precalc.add(b)
	}
	return precalc
}

private fun solve() {
	val (n, k) = readInts()
	val a = precalc.getOrNull(k - 1)
	if (a == null || a.size > n) {
		out.appendLine("-1")
		return
	}
	var first = true
	for (x in a) {
		if (!first) out.append(" ")
		first = false
		out.append((x + 1).toString())
	}
	for (i in a.size until n) {
		out.append(" ")
		out.append((i + 1).toString())
	}
	out.appendLine()
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

private val out = System.out.bufferedWriter()

fun main() = repeat(readInt()) { solve() }
	.also { out.close() }
