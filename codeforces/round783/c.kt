package codeforces.round783

fun main() {
	val n = readInt()
	val a = (1..n).first { a -> (n - a) * 2 - 1 <= a}
	val b = n - a
	val ans = List(a) { x ->
		if (x < b) b - 1 - x else if (x < 2 * b - 1) b + (b - 2 - (x - b)) else x
	}
	println(ans.size)
	println(ans.withIndex().joinToString("\n") { "${it.index + 1} ${it.value + 1}" })
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
