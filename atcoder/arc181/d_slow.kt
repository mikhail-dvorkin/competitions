package atcoder.arc181

fun main() {
	readln()
	val p = readInts().map { it - 1 }.toIntArray()
	val pReversed = p.reversedPermutation()
	readln()
	val operations = (readInts() + p.size).toIntArray()
	val ans = IntArray(operations.size + 1)
	var inv = 0
	for (i in p.indices) {
		val iPosition = pReversed[i]
		var smallerLeft = 0
		for (j in 0 until iPosition) if (p[j] < i) smallerLeft++
		val largerLeft = iPosition - smallerLeft
		inv += largerLeft
		val firstOp = operations.indexOfFirst { iPosition < it }
		val lastOp = minOf(firstOp + largerLeft, operations.size)
		for (k in firstOp until lastOp) ans[k]++
	}
	for (i in 0..ans.size - 2) {
		if (i > 0) println(inv)
		inv -= ans[i]
	}
}

private fun IntArray.reversedPermutation() = IntArray(size).also { for (i in indices) it[this[i]] = i }

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
