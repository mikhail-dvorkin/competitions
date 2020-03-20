package codeforces.globalround7

fun main() {
	readLn()
	val b = readInts()
	val a = MutableList(b.size) { 0 }
	var max = 0
	for (i in a.indices) {
		a[i] = b[i] + max
		max = maxOf(max, a[i])
	}
	println(a.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
