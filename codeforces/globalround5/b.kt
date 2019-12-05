package codeforces.globalround5

fun main() {
	val n = readInt()
	val a = readInts().map { it - 1 }
	val b = readInts().map { it - 1 }
	val bRev = IntArray(n)
	for (i in b.indices) bRev[b[i]] = i
	var max = -1
	var ans = 0
	for (x in a.map { bRev[it] }) if (max > x) ans++ else max = x
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
