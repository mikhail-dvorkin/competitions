package codechef.lunchtime2022_08

private fun solve() {
	readLn()
	val a = readInts()
	val frequent = a.groupBy { it }.values.maxOf { it.size }
	println(if (frequent <= (a.size + 1) / 2) "Yes" else "No")
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
