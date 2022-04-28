package gcj.y2022.round1b

private fun solve(): Int {
	readLn()
	val a = readInts()
	var maxSeen = 0
	var low = 0
	var high = a.size
	var ans = 0
	while (low < high) {
		val taken = if (a[low] <= a[high - 1]) a[low++] else a[--high]
		if (taken >= maxSeen) {
			ans++
			maxSeen = taken
		}
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
