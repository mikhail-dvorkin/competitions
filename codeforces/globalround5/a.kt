package codeforces.globalround5

fun main() {
	var mode = 0
	repeat(readInt()) {
		val x = readInt()
		if (x % 2 == 0) {
			println(x / 2)
		} else {
			println((x - 1) / 2 + mode)
			mode = 1 - mode
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
