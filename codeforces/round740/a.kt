package codeforces.round740

private fun solve() {
	val (a, b) = readInts().sorted()
	val ans = mutableSetOf<Int>()
	for (first in 0..(a + b) % 2) {
		val aGive = (a + b) / 2 + first
		for (bBreak in 0..minOf(b, aGive)) {
			val aBreak = a - (aGive - bBreak)
			if (aBreak < 0) continue
			ans.add(aBreak + bBreak)
		}
	}
	println(ans.size)
	println(ans.sorted().joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
