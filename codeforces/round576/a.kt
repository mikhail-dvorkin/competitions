package codeforces.round576

fun main() {
	val (n, sizeBytes) = readInts()
	val a = readInts().toIntArray()
	a.sort()
	val count = mutableListOf<Int>()
	var start = 0
	for (i in a.indices) {
		if ((i == a.size - 1) || (a[i] != a[i + 1])) {
			count.add(i + 1 - start)
			start = i + 1
		}
	}
	val size = sizeBytes * 8
	val bitsPerPoint = minOf(size / n, 30)
	val options = 1 shl bitsPerPoint
//	val keys = a.toSet().toList().sorted()
//	val grouping = a.groupBy { it }
//	val count = IntArray(keys.size) { grouping[keys[it]]!!.size }
	var saved = 0
	for (i in 0 until minOf(options, n)) {
		saved += count[i]
	}
	var ans = saved
	for (i in 1 until n) {
		if (i + options - 1 >= count.size) {
			break
		}
		saved -= count[i - 1]
		saved += count[i + options - 1]
		ans = maxOf(ans, saved)
	}
	println(n - ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
