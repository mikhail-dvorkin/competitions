package atcoder.arc128

fun main() {
	val n = readInt()
	val a = readInts()
	val bestGold = DoubleArray(n + 1)
	val bestSilver = DoubleArray(n + 1)
	val howGold = IntArray(n + 1)
	val howSilver = IntArray(n + 1)
	bestGold[0] = 1.0
	for (i in a.indices) {
		val newGold = bestSilver[i] / a[i]
		if (newGold > bestGold[i]) {
			bestGold[i + 1] = newGold
			howGold[i + 1] = 1
		} else {
			bestGold[i + 1] = bestGold[i]
		}
		val newSilver = bestGold[i] * a[i]
		if (newSilver > bestSilver[i]) {
			bestSilver[i + 1] = newSilver
			howSilver[i + 1] = 1
		} else {
			bestSilver[i + 1] = bestSilver[i]
		}
		bestSilver[i + 1] /= bestGold[i + 1]
		bestGold[i + 1] = 1.0
	}
	var gold = true
	val ans = IntArray(n)
	for (i in n downTo 1) {
		if (gold) {
			if (howGold[i] == 1) {
				gold = false
				ans[i - 1] = 1
			}
		} else {
			if (howSilver[i] == 1) {
				gold = true
				ans[i - 1] = 1
			}
		}
	}
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
