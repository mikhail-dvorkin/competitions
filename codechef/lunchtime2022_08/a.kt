package codechef.lunchtime2022_08

private fun solve() {
	val n = readInt()
	println((n downTo 1).joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
