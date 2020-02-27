package codeforces.kotlinheroes3.practice

fun main() {
	val (_, m) = readInts()
	val a = readInts()
	println(medianLessThan(a, m + 1) - medianLessThan(a, m))
}

private fun medianLessThan(a: List<Int>, m: Int): Long {
	var x = a.size
	val count = MutableList(2 * x + 1) { 0 }.also { it[x] = 1 }
	return a.fold(0L to 1, { (ans, leq), v ->
		val leqShifted = leq + if (v < m) count[++x] else -count[x--]
		count[x]++
		ans + leqShifted to leqShifted + 1
	}).first
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
