package codeforces.vk2021.round1

private fun solve() {
	val (hei, wid) = readInts()
	val f = List(hei) { MutableList(wid) { 0 } }
	for (i in 0..wid - 3) {
		if (i % 2 == 0) {
			f.first()[i] = 1
			f.last()[i] = 1
		}
	}
	f.first()[wid - 1] = 1
	f.last()[wid - 1] = 1
	for (i in 2..hei - 3) {
		if (i % 2 == 0) {
			f[i][0] = 1
			f[i][wid - 1] = 1
		}
	}
	for (i in 0 until hei) println(f[i].joinToString(""))
	println()
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
