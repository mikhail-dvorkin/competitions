package codeforces.round626

fun main() {
	readLn()
	var a = readInts().sorted()
	val powers = a.last().toString(2).length downTo 0
	val ans = powers.sumBy { k ->
		val two = 1 shl k
		val res = (1..4).sumBy { countLess(a, it * two) } and 1
		a = a.map { it and two - 1 }.sorted()
		two * res
	}
	println(ans)
}

private fun countLess(a: List<Int>, value: Int): Int {
	var j = a.lastIndex
	return a.indices.sumBy { i ->
		while (j >= 0 && a[i] + a[j] >= value) j--
		minOf(i, j + 1)
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
