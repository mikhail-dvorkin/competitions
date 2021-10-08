package codeforces.kotlinheroes8

private fun solve() {
	var minK = 0
	var maxK = Int.MAX_VALUE
	repeat(readInt()) {
		val (s, t, should) = readStrings()
		val x = s.reversed().zip(t.reversed()).takeWhile { it.first == it.second }.size
		if (should == "1") {
			maxK = minOf(maxK, x)
		} else {
			minK = maxOf(minK, x + 1)
		}
	}
	val ans = minK..maxK
	println(ans.count())
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
