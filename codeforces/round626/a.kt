package codeforces.round626

@ExperimentalStdlibApi
fun main() {
	readLn()
	val s = readLn()
	val balances = s.scan(0) { acc, c -> acc + if (c == '(') 1 else - 1 }
	val negative = balances.zipWithNext().count { it.first + it.second < 0 }
	println(if (balances.last() == 0) negative else -1)
}

private fun readLn() = readLine()!!
