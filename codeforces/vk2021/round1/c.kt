package codeforces.vk2021.round1

private fun solve() {
	readLn()
	val a = readInts()
	val b = readInts()
	var low = -1
	var high = 4 * a.size
	while (low + 1 < high) {
		val extra = (low + high) / 2
		fun score(array: List<Int>, new: Int): Int {
			val scores = array + List(extra) { new }
			return scores.sorted().takeLast(scores.size - scores.size / 4).sum()
		}
		val my = score(a, 100)
		val his = score(b, 0)
		if (my >= his) high = extra else low = extra
	}
	println(high)
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
