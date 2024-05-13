package codeforces.kotlinheroes10.practice

import java.util.TreeMap

private fun solve() {
	readln()
	val (_, m) = readInts()
	val a = readInts().toIntArray()
	val sequence = IncrementalIncreasingSequence()
	for (i in a.indices) {
		sequence.add(i, -a[i])
	}
	val ans = IntArray(m) {
		val (kIn, d) = readInts()
		val k = kIn - 1
		a[k] -= d
		sequence.add(k, -a[k])
		sequence.size()
	}
	println(ans.joinToString(" "))
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { solve() }

data class IncrementalIncreasingSequence(val map: TreeMap<Int, Int>) {
	constructor() : this(TreeMap())

	init {
		map[Int.MIN_VALUE] = Int.MIN_VALUE
		map[Int.MAX_VALUE] = Int.MAX_VALUE
	}

	fun size() = map.size - 2

	fun add(key: Int, value: Int) {
		val floorEntry = map.floorEntry(key)
		if (floorEntry.value >= value) return
		map[key] = value
		while (true) {
			val entry = map.higherEntry(key)
			if (entry.value > value) break
			map.remove(entry.key)
		}
	}
}
