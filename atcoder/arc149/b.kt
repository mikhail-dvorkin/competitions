package atcoder.arc149

fun main() {
	readLn()
	val a = readInts()
	val b = readInts()
	val c = a.zip(b).sortedBy { it.first }.map { it.second }
	val bestEnd = mutableListOf<Int>()
	for (x in c) {
		val pos = (-1..bestEnd.size).binarySearch { bestEnd[it] > x }
		if (pos == bestEnd.size) bestEnd.add(x) else bestEnd[pos] = x
	}
	println(c.size + bestEnd.size)
}

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
