package gcj.y2023.farewell_a

private fun solve(): String {
	val (m, r, _) = readInts()
	val xs = readInts()
	var x = 0
	var i = 0
	var answer = 0
	while (x < m) {
		while (i + 1 < xs.size && xs[i + 1] <= x + r) i++
		if (i >= xs.size || xs[i] > x + r) return "IMPOSSIBLE"
		x = xs[i] + r
		i++
		answer++
	}
	return "" + answer
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
