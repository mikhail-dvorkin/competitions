package codeforces.round631

fun main() {
	val (n, _) = readInts()
	val a = readInts()
	if (a.fold(0L, Long::plus) < n || a.indices.any { it + a[it] > n }) return println(-1)
	val p = IntArray(a.size) { it }
	for (i in p.indices.reversed()) {
		p[i] = maxOf(p[i], p.getOrElse(i + 1) { n } - a[i])
	}
	println(p.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
