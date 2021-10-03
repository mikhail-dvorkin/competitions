package yandex.y2020.qual

private fun solve(): Boolean {
	readLn()
	val a = readInts().sorted()
	readLn()
	val b = readInts().sorted()
	if (a.size != b.size) return false
	if (a.isEmpty()) return true
	val aSame = a.all { it == a[0] }
	val bSame = b.all { it == b[0] }
	if (aSame && bSame) return true
	if (aSame || bSame) return false
	return good(a, b) || good(a, b.reversed())
}

private fun good(a: List<Int>, b: List<Int>): Boolean {
	return a.indices.all {
		(a[it] - a.first()).toLong() * (b.last() - b.first()) ==
				(b[it] - b.first()).toLong() * (a.last() - a.first())
	}
}

fun main() = repeat(readInt()) { println(if (solve()) "YES" else "NO") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().filter { it.isNotEmpty() }.map { it.toInt() }
