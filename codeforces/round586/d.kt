package codeforces.round586

fun main() {
	readLn()
	val a = readStrings().map { it.toLong() }
	val saved = a.groupBy(java.lang.Long::numberOfTrailingZeros).values.maxByOrNull { it.size }!!
	val removed = a.minus(saved)
	println(removed.size)
	println(removed.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
