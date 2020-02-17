package codeforces.round621

private fun solve() {
	val (n, d) = readInts()
	val a = readInts().toMutableList()
	var left = d
	var ans = a[0]
	for (i in 1 until n) {
		while (a[i] > 0 && i <= left) {
			a[i]--
			left -= i
			ans++
		}
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
