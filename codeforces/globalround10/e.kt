package codeforces.globalround10

fun main() {
	val n = readInt()
	for (i in 0 until n) {
		for (j in 0 until n) {
			print((i % 2) * (1L shl (i + j - 1)))
			print(" ")
		}
		println()
	}
	repeat(readInt()) {
		val sum = readLong()
		var (x, y) = 1 to 1
		for (i in 0 until 2 * n - 1) {
			println("$x $y")
			if (((sum shr i) and 1L) == 1L) {
				if (x % 2 == 1) x++ else y++
			} else {
				if (x % 2 == 0) x++ else y++
			}
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readLong() = readLn().toLong()
