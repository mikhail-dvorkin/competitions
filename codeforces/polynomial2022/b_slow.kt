package codeforces.polynomial2022

import java.util.TreeSet

private fun solve(): String {
	val (n, _, k) = readInts()
	val a = readInts()
	val allow = LongArray(n) { -1 }
	val allowed = TreeSet<Long>()
	fun encode(count: Int, index: Int) = (count.toLong() shl 32) + index.toLong()
	for (i in a.indices) {
		allowed.add(encode(a[i], i))
	}
	for (i in 0 until n) {
		if (allow[i] >= 0) allowed.add(allow[i])
		val use = allowed.pollLast() ?: return "NO"
		val count = (use shr 32).toInt()
		val index = use.toInt()
		if (count > 1 && i + k < n) allow[i + k] = encode(count - 1, index)
	}
	return "YES"
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
