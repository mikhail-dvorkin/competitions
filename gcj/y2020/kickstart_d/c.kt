package gcj.y2020.kickstart_d

private fun solve(): Double {
	val (n, a, b) = readInts()
	val p = listOf(0, 0) + readInts()
	val p1 = probs(p, a)
	val p2 = probs(p, b)
	val x = (1 until p.size).map { p1[it].toLong() * p2[it] }.sum()
	return p.size - 1 - x / (p.size - 1.0) / (p.size - 1)
}

private fun probs(p: List<Int>, a: Int): IntArray {
	val q = 22
	val n = p.size
	val r = List(q) { IntArray(n) }
	for (i in p.indices) r[0][i] = p[i]
	for (j in 1 until q) {
		for (i in p.indices) r[j][i] = r[j - 1][r[j - 1][i]]
	}
	val up = IntArray(n) { i ->
		var k = i
		for (j in 0 until q) {
			if (((a shr j) and 1) == 0) continue
			k = r[j][k]
		}
		k
	}
	val below = IntArray(n)
	for (i in below.indices.reversed()) {
		below[i]++
		below[up[i]] += below[i]
	}
	return IntArray(n) { n - 1 - below[it] }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ").filter { it.isNotEmpty() }
private fun readInts() = readStrings().map { it.toInt() }
