package codeforces.round576

fun main() {
	val (n, sizeInBytes) = readInts()
	val sortedMap = sortedMapOf<Int, Int>()
	for (x in readInts()) {
		sortedMap[x] = sortedMap.getOrDefault(x, 0) + 1
	}
	val count = sortedMap.values.toList()
	val options = minOf(1 shl minOf(sizeInBytes * 8 / n, 30), count.size)
	var saved = count.take(options).sum()
	var ans = saved
	for (i in 0 until count.size - options) {
		saved += count[i + options] - count[i]
		ans = maxOf(ans, saved)
	}
	println(n - ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
