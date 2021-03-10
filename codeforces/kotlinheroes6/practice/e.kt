package codeforces.kotlinheroes6.practice

fun main() {
	var left = 0; var right = 0
	val position = mutableMapOf<Int, Int>()
	repeat(readInt()) {
		val (type, idString) = readStrings()
		val id = idString.toInt()
		if (type != "?") {
			position[id] = if (type == "L") --left else right++
		} else {
			println(position[id]!!.let { minOf(it - left, right - 1 - it) })
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
