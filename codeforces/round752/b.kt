package codeforces.round752

private fun solve(): Long {
	val (x, y) = readInts()
	val n = solve(x, y)
	if (n % x != y % n) error("")
	return n
}

private fun solve(x: Int, y: Int): Long {
	if (y < x) {
		return x.toLong() * x + y
	}
	if (y % x == 0) return x.toLong()
	val p = y.toLong() - y % x
	return (p + y) / 2
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
