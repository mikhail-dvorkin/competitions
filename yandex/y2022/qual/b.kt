package yandex.y2022.qual

private fun solve() {
	val (nIn, bIn) = readStrings()
	val b = bIn.toInt()
	val n = (nIn.toLong() + 1).toString().map { it.toString().toInt() }
	var power = 1L
	var ans = 0L
	val leftBad = n.indexOfFirst { it % b != 0 }.takeIf { it >= 0 } ?: n.size
	val powerBase = 9 / b + 1
	for (i in n.indices.reversed()) {
		if (i <= leftBad && n[i] > 0) ans += power * ((n[i] - 1) / b + 1)
		power *= powerBase
	}
	println(ans - 1)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
