package codeforces.globalround5

fun main() {
	val (h, w, n) = readInts()
	val rTaken = BooleanArray(h)
	val cTaken = BooleanArray(w)
	repeat(n) {
		val (r1, c1, r2, c2) = readInts().map { it - 1 }
		rTaken[r1] = true
		rTaken[r2] = true
		cTaken[c1] = true
		cTaken[c2] = true
	}
	val rFree = free(rTaken)
	val cFree = free(cTaken)
}

fun free(taken: BooleanArray): MutableList<Int> {
	val n = taken.size
	val res = mutableListOf<Int>()
	for (i in taken.indices) {
		if (taken[i] || (i > 0 && !taken[i - 1])) continue
		for (j in i..n) {
			if (j == n || taken[j]) {
				res.add(j - i)
				break
			}
		}
 	}
	res.sort()
	return res
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
