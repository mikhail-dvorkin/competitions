package yandex.y2021.qual

fun main() {
	val (hei, wid) = readInts()
	val f = List(hei) { readLn().map { it - '0' } }
	val countZero = f.sumOf { row -> row.count { it == 0 } }
	val inf = hei * wid * 3
	val a = List(wid + 1) { List(hei + 1) { IntArray(wid * hei + 1) { inf } } }
	for (y in 0..hei) a[0][y][0] = 0
	for (x in 0 until wid) {
		val ones = IntArray(hei + 1)
		for (y in 0 until hei) {
			ones[y + 1] = ones[y] + f[y][x]
		}
		val prev = a[x]
		val next = a[x + 1]
		for (y in 0..hei) {
			val prevY = prev[y]
			val nextY = next[y]
			val onesY = ones[y]
			for (z in x * y..wid * hei - y) {
				nextY[z + y] = minOf(nextY[z + y], prevY[z] + onesY)
			}
		}
		for (y in hei - 1 downTo 0) {
			val nextY = next[y]
			val nextY1 = next[y + 1]
			for (z in 0..wid * hei) {
				nextY[z] = minOf(nextY[z], nextY1[z])
			}
		}
	}
	println(a[wid][0][countZero])
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
