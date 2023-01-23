package atcoder.arc154

private fun solve(): Boolean {
	readLn()
	val a = readInts()
	val b = readInts()
	val bSet = b.toSet()
	if (a == b) return true
	if (bSet.size == 1) return a.contains(b[0])
	val want = mutableListOf<Int>()
	for (k in b.indices) if (b[k] != b[(k + 1) % b.size]) want.add(b[k])
	if (want.size == b.size) return false
	for (aShift in a.indices) {
		var k = 0
		var i = aShift
		for (ii in a.indices) {
			if (a[i] == want[k]) k++
			if (k == want.size) return true
			i += 1
			if (i == a.size) i = 0
		}
	}
	return false
}

fun main() = repeat(readInt()) { println(if (solve()) "Yes" else "No") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
