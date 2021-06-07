package codeforces.round616

private fun solve(): Int {
	val (n, mIn, kIn) = readInts()
	val m = mIn - 1
	val k = minOf(kIn, m)
	val a = readInts()
	val b = List(m + 1) { maxOf(a[it], a[it + n - 1 - m]) }
	return List(k + 1) { b.drop(it).take(m + 1 - k).minOrNull()!! }.maxOrNull()!!
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
