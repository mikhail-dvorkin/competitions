package codeforces.round586

fun main() {
	readLn()
	val s = readLn()
	val ans = mutableListOf<Int>()
	ans.addAll(List(s.count { it == 'n' }) {1})
	ans.addAll(List(s.count { it == 'z' }) {0})
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
