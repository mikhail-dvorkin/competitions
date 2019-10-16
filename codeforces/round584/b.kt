package codeforces.round584

fun main() {
	val n = readInt()
	val init = readLn()
	val steps = 0..130
	val on = List(n) {
		val (a, b) = readInts()
		var x = (init[it] == '1')
		val data = mutableListOf<Boolean>()
		for (i in steps) {
			if (i >= b && (i - b) % a == 0) {
				x = !x
			}
			data.add(x)
		}
		data
	}
	println(steps.map { i -> on.map { it[i] }.count { it } }.max())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
