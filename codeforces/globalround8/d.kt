package codeforces.globalround8

fun main() {
	readLn()
	val a = readInts()
	val bs = 20
	val bitCount = IntArray(bs)
	for (x in a) {
		for (i in bitCount.indices) {
			bitCount[i] += x.bit(i)
		}
	}
	var ans = 0L
	for (j in a.indices) {
		var x = 0
		for (i in bitCount.indices) {
			if (j < bitCount[i]) {
				x += 1 shl i
			}
		}
		ans += x.toLong() * x
	}
	println(ans)
}

private fun Int.bit(index: Int) = shr(index) and 1
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
