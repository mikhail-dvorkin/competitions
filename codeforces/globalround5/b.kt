package codeforces.globalround5

fun main() {
	val n = readInt()
	val a = readInts().map { it - 1 }
	val b = readInts().map { it - 1 }
	val aRev = IntArray(n)
	val bRev = IntArray(n)
	for (i in a.indices) {
		aRev[a[i]] = i
		bRev[b[i]] = i
	}
	val p = IntArray(n)
	for (i in a.indices) {
		p[aRev[i]] = bRev[i]
	}
	var max = -1
	var ans = 0
	for (x in p) {
		if (max > x) ans++ else max = x
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
