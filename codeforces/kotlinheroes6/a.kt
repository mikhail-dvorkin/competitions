package codeforces.kotlinheroes6

private fun solve() {
	val (x, yIn) = readInts()
	var y = yIn.toLong()
	var ans = 0
	for (t in 9 downTo 0) {
		val a = Math.pow(10.0, t.toDouble()).toLong() * x
		while (a <= y) {
			y -= a
			ans++
		}
	}
	println(ans + y)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
