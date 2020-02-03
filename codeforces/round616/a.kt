package codeforces.round616

private fun solve() {
	val (n, mIn, kIn) = readInts()
	val m = mIn - 1
	val k = minOf(kIn, m)
	val a = readInts()
	val b = mutableListOf<Int>()
	for (i in 0..m) {
		b.add(maxOf(a[i], a[i + n - 1 - m]))
	}
	var ans = a.min()!!
	for (i in 0..k) {
		ans = maxOf(ans, b.subList(i, b.size - k + i).min()!!)
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
