package codeforces.vk2022.qual

private fun solve() {
	readln()
	val a = readInts()
	var ans = 1
	for (i in a.indices.reversed()) {
		for (t in 1 downTo 0) {
			val high = a[i] + t
			var j = i
			var low = high
			while (true) {
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
