package codeforces.kotlinheroes6

private fun solve() {
	val (n, sum) = readLongs()
	var low = 1L
	var high = sum + 1L
	while (low + 1 < high) {
		val last = (low + high) / 2
		var cur = last
		var need = 0L
		for (i in 0 until n) {
			if (cur == 1L) {
				need += n - i
				break
			}
			need += cur
			cur = (cur + 1) / 2
		}
		if (need <= sum) low = last else high = last
	}
	println(low)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
