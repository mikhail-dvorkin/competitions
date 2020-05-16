package gcj.y2020.round2

fun solvePancakes(aIn: Long, bIn: Long): String {
	var a = aIn
	var b = bIn
	val maxN = (Math.pow(a.toDouble() + b + 100, 0.5) * 3).toLong()
	var i = 1L

	fun go(): String? {
		if (maxOf(a, b) < i) return "${i - 1} $a $b"
		if (a >= b) a -= i else b -= i
		i++
		return null
	}

	if (a >= b) {
		val j = (i-1 .. maxN).binarySearch { j -> sum(i, j) >= a - b } - 1
		a -= sum(i, j)
		i = j + 1
	}
	go()?.also { return it }
	if (b >= a) {
		val j = (i-1 .. maxN).binarySearch { j -> sum(i, j) >= b - a } - 1
		b -= sum(i, j)
		i = j + 1
	}
	if (b > a) { go()?.also { return it } }
	val take = ((i-1..maxN).binarySearch { j -> sum(i, j) >= a + b } - i - 1).coerceAtLeast(0) / 2
	a -= (i + take - 1) * take
	b -= (i + take) * take
	i += 2 * take
	repeat(10000) {
		go()?.also { return it }
	}
	return "?"
}

private fun solve(): String {
	val (a, b) = readLongs()
	return solvePancakes(a, b)
}

private fun sum(start: Long, end: Long) = (start + end) * (end + 1 - start) / 2

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun LongRange.binarySearch(predicate: (Long) -> Boolean): Long {
	var (low, high) = this.first to this.last // must be false .. must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
