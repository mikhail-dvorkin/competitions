package atcoder.arc148

fun main() {
	val (n, m) = readInts()
	val a = readInts().sorted()

	val oddArray = IntArray(2 * n)
	var oddSize = 0
	for (i in a.indices) {
		if (i > 0 && a[i - 1] == a[i]) continue
		var j = i
		while (j + 1 < a.size && a[j + 1] == a[i]) j++
		if ((j - i) % 2 == 0) {
			oddArray[oddSize++] = a[i]
		}
	}
	val odd = oddArray.take(oddSize).toSet()

//	val odd = a.groupBy { it }.filter { it.value.size % 2 == 1 }.keys
	var inPairs = 0
	if (m % 2 == 0) for (x in odd) {
		val y = x + m / 2
		if (y < m && y in odd) {
			inPairs += 2
		}
	}
	println(if ((inPairs == odd.size) && inPairs % 4 == 0) "Bob" else "Alice")
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
