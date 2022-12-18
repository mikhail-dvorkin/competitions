package codeforces.vk2022.qual

private fun solve() {
	readln()
	val a = readInts()
	var ans = 1
	var nextHigh = a.last() + 1
	val seen = mutableSetOf<Pair<Int, Int>>()
	for (i in a.indices.reversed()) {
		for (t in 1 downTo 0) {
			val high = a[i] + t
			if (high > nextHigh) continue
			nextHigh = nextHigh.coerceAtMost(high - 1)
			var j = i
			var low = high
			while (true) {
				if (!seen.add(low to j)) break
				while (j > 0 && a[j] > low) j--
				if (j < 0 || a[j] < low - 1 || a[j] > low) break
				low--
				j--
			}
			ans = ans.coerceAtLeast(high - low)
		}
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
