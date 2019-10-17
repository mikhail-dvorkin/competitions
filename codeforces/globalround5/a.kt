package codeforces.globalround5

import kotlin.math.floor

fun main() {
	var mode = 0
	repeat(readInt()) {
		val x = readInt()
		if (x % 2 == 0) {
			println(x / 2)
		} else {
			println(floor(x / 2.0).toInt() + mode)
			mode = 1 - mode
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
