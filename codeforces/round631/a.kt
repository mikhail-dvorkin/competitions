package codeforces.round631

fun main() {
	val (n, m) = readInts()
	val len = readInts()
	if (len.sumLong() < n) return println(-1)
	val p = IntArray(m)
	for (i in p.indices) {
		p[i] = i
		if (p[i] + len[i] > n) return println(-1)
	}
	var edge = n
	for (i in p.indices.reversed()) {
		if (edge - len[i] <= p[i]) break
		p[i] = edge - len[i]
		edge = p[i]
	}
	println(p.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun Iterable<Int>.sumLong(): Long {
	var sum = 0L
	for (element in this) {
		sum += element
	}
	return sum
}
