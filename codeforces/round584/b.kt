package codeforces.round584

fun main() {
	val steps = 125
	readLn()
	val init = readLn()
	val on = init.map { c ->
		val (a, b) = readInts()
		var x = (c == '1')
		List(steps) { if (it >= b && (it - b) % a == 0) x = !x; x }
	}
	println(List(steps) { i -> on.count { it[i] } }.maxOrNull())
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
