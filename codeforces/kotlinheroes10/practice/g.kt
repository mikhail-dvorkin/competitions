package codeforces.kotlinheroes10.practice

fun solve(a: List<Int>, hInit: Int, indexOfTriple: Int, serums: Int = 3): Int {
	var i = 0
	var s = 0
	var h = hInit.toLong()
	while (i < a.size) {
		if (a[i] < h) {
			h += a[i] / 2
			i++
			continue
		}
		if (s == serums) break
		h *= if (s == indexOfTriple) 3 else 2
		s++
	}
	return i
}

private fun solve() {
	val (_, h) = readInts()
	val a = readInts().sorted()
	println((0..2).maxOf { solve(a, h, it) })
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(readInt()) { solve() }
