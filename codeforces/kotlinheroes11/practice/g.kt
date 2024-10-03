package codeforces.kotlinheroes11.practice

private fun solve() {
	readln()
	val a = (listOf(0) + readInts() + 0).toIntArray()
	val d = (listOf(Int.MAX_VALUE) + readInts() + Int.MAX_VALUE).toIntArray()
	val prev = IntArray(a.size) { it - 1 }
	val next = IntArray(a.size) { it + 1 }
	var toCheck = (1..a.size - 2).toMutableList()
	for (round in 0 until a.size - 2) {
		val die = mutableListOf<Int>()
		for (x in toCheck) {
			val attacked = a[prev[x]] + a[next[x]]
			if (attacked > d[x]) die.add(x)
		}
		toCheck.clear()
		for (x in die) {
			next[prev[x]] = next[x]
			prev[next[x]] = prev[x]
			toCheck.add(prev[x])
			toCheck.add(next[x])
		}
		toCheck = toCheck.toSet().minus(die.toSet()).minus(0).minus(a.lastIndex).toMutableList()
		out.append("${die.size} ")
	}
	out.appendLine()
}

fun main() = repeat(readInt()) { solve() }.also { out.close() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private val out = System.out.bufferedWriter()
