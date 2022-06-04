package gcj.y2022.round3

private fun solve(): Long {
	val (n, colors) = readInts()
	val constraints = List(colors) { readInts() }
	val hats = readInts().map { it - 1 }
	val byColor = List(colors) { mutableListOf<Int>() }
	val indexInColor = IntArray(colors)
	for (i in hats.indices) {
		byColor[hats[i]].add(i)
	}
	val x = IntArray(colors)
	val y = IntArray(colors)
	val z = IntArray(colors)
	fun fillXYZ(c: Int, start: Int) {
		val (lowIn, highIn) = constraints[c]
		val ofC = byColor[c]
		if (ofC.size == 0) {
			x[c] = 2 * n; y[c] = -1; z[c] = -1
			return
		}
		val low = maxOf(lowIn, 1)
		val high = minOf(highIn, ofC.size)
		if (start > 0) indexInColor[c]++
		val index = indexInColor[c]
		fun get(i: Int) = if (i < ofC.size) ofC[i] else (ofC[i - ofC.size] + n)
		x[c] = get(index) + 1
		if (ofC.size < low) {
			y[c] = -1; z[c] = -1
		} else {
			y[c] = get(index + low - 1) + 1
			z[c] = if (high >= ofC.size) 2 * n else get(index + high) + 1
		}
	}
	val good = IntArray(2 * n)
	fun add(from: Int, to: Int, value: Int) {
		if (from >= to) return
		for (k in from until to) good[k] += value
	}
	fun count(start: Int): Int {
		var perfects = 0
		for (k in 2 until n) if (good[start + k] == colors) perfects++
		return perfects
	}
	for (c in constraints.indices) {
		fillXYZ(c, 0)
		add(0, x[c], 1)
		add(y[c], z[c], 1)
	}
	var ans = count(0).toLong()
	for (i in 0..n - 2) {
		val c = hats[i]
		add(0, x[c], -1)
		add(y[c], z[c], -1)
		fillXYZ(c, i + 1)
		add(0, x[c], 1)
		add(y[c], z[c], 1)
		ans += count(i + 1)
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
