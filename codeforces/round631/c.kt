package codeforces.round631

import java.io.*

private fun solve() {
	val (h, g) = readInts()
	val a = (listOf(0) + readInts()).toIntArray()
//	val h2 = 1 shl h
	val h3 = 1 shl (h - 1)
	val g2 = 1 shl g
//	val g3 = 1 shl (g - 1)
	val ans = mutableListOf<Int>()
	for (r in 1 until g2) {
		while (true) {
			var x = r
			while (true) {
				if (x >= g2 || a[x] == 0) break
				x = if (a[2 * x] >= a[2 * x + 1]) 2 * x else 2 * x + 1
			}
			if (a[x] == 0) break
			ans.add(r)
			x = r
			while (true) {
				if (x >= h3 || a[x] == 0) {
					a[x] = 0
					break
				}
				x = if (a[2 * x] >= a[2 * x + 1]) 2 * x else 2 * x + 1
				a[x / 2] = a[x]
			}
		}
	}
	var sum = 0L
	a.forEach { sum += it }
	println(sum)
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private val br = BufferedReader(InputStreamReader(System.`in`))
private fun readLn() = br.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
