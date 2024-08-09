package atcoder.arc181

fun main() {
	readln()
	val p = readInts().map { it - 1 }.toIntArray()
	val pReversed = p.reversedPermutation()
	readln()
	val operations = (readInts() + p.size).toIntArray()

	val fenwickTree = FenwickTree(p.size)
	val smallerLeft = IntArray(p.size)
	for (i in p.indices) {
		smallerLeft[i] = fenwickTree.sum(p[i])
		fenwickTree.add(p[i], 1)
	}

	var inv = 0L
	val ansShift = IntArray(operations.size + 1)
	for (i in p.indices) {
		val iPosition = pReversed[i]
		val largerLeft = iPosition - smallerLeft[iPosition]
		inv += largerLeft
		val firstOp = (-1..operations.size).binarySearch { operations[it] > iPosition }
		val lastOp = minOf(firstOp + largerLeft, operations.size)
		ansShift[firstOp] += 1
		ansShift[lastOp] -= 1
	}
	var shift = 0
	val ans = IntArray(operations.size + 1)
	for (i in ans.indices) {
		shift += ansShift[i]
		ans[i] = shift
	}
	for (i in 0..ans.size - 2) {
		if (i > 0) println(inv)
		inv -= ans[i]
	}
}

private fun IntArray.reversedPermutation() = IntArray(size).also { for (i in indices) it[this[i]] = i }

private fun IntRange.binarySearch(predicate: (Int) -> Boolean): Int {
	var (low, high) = this.first to this.last // must be false ... must be true
	while (low + 1 < high) (low + (high - low) / 2).also { if (predicate(it)) high = it else low = it }
	return high // first true
}

class FenwickTree(n: Int) {
	var t: IntArray = IntArray(n)

	fun add(i: Int, value: Int) {
		var j = i
		while (j < t.size) {
			t[j] += value
			j += (j + 1) and -(j + 1)
		}
	}

	fun sum(i: Int): Int {
		var j = i
		var res = 0
		j--
		while (j >= 0) {
			res += t[j]
			j -= (j + 1) and -(j + 1)
		}
		return res
	}
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
