package gcj.y2020.kickstart_c

private fun solve(): Long {
	readLn()
	val pref = mutableMapOf(0 to 1)
	var prefSum = 0
	var ans = 0L
	var minPrefSum = 0
	for (x in readInts()) {
		prefSum += x
		for (i in 0..Int.MAX_VALUE) {
			ans += pref.getOrDefault(prefSum - i * i, 0)
			if (prefSum - i * i < minPrefSum) break
		}
		pref[prefSum] = pref.getOrDefault(prefSum, 0) + 1
		minPrefSum = minOf(minPrefSum, prefSum)
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
